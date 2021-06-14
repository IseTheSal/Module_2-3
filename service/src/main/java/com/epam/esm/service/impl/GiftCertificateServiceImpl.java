package com.epam.esm.service.impl;

import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.RestErrorStatusCode;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String UPDATE_OPTION = "UPDATE";
    private static final String CREATE_OPTION = "CREATE";
    private static final String ASC_SORT = "ASC";
    private static final String DESC_SORT = "DESC";

    private final GiftCertificateDao giftCertificateDao;
    //fixme ask if it`s correct
    private MessageSource messageSource;
    private final Locale locale = new Locale("ru", "RU");

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        checkCertificateValid(giftCertificate, CREATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        giftCertificate.setCreateDate(currentDateTime);
        giftCertificate.setLastUpdateDate(currentDateTime);
        return giftCertificateDao.create(giftCertificate);
    }

    private void checkTagsValid(Set<Tag> tagSet) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        for (Tag tag : tagSet) {
            if (!TagValidator.isNameValid(tag.getName())) {
                exceptionValidMessage.append(messageSource.getMessage("error.validation.name",
                        new Object[]{tag.getName()},
                        locale));
            }
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message, RestErrorStatusCode.VALIDATION_ERROR);
        }
    }

    private void checkCertificateValid(GiftCertificate giftCertificate, String option) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        String name = giftCertificate.getName();
        if (((name != null) && !GiftCertificateValidator.isNameValid(name) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isNameValid(name) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.name",
                    new Object[]{name},
                    locale));
        }
        String description = giftCertificate.getDescription();
        if (((description != null)
                && !GiftCertificateValidator.isDescriptionValid(description) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isDescriptionValid(description) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.description",
                    new Object[]{description},
                    locale));
        }
        BigDecimal price = giftCertificate.getPrice();
        if (((price != null) && !GiftCertificateValidator.isPriceValid(price) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isPriceValid(price) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.price",
                    new Object[]{price},
                    locale));
        }
        Integer duration = giftCertificate.getDuration();
        if (((duration != null) && !GiftCertificateValidator.isDurationValid(duration) && option.equals(UPDATE_OPTION))
                || (!GiftCertificateValidator.isDurationValid(duration) && option.equals(CREATE_OPTION))) {
            exceptionValidMessage.append(messageSource.getMessage("error.gift.validation.duration",
                    new Object[]{duration},
                    locale));
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message, RestErrorStatusCode.VALIDATION_ERROR);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        if (!giftCertificateDao.findById(id).isPresent()) {
            throw new GiftCertificateNotFoundException(messageSource.getMessage("error.gift.not.found",
                    new Object[]{id},
                    locale),
                    RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
        checkCertificateValid(giftCertificate, UPDATE_OPTION);
        checkTagsValid(giftCertificate.getTags());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now());
        return giftCertificateDao.update(giftCertificate);
    }

    @Override
    public GiftCertificate findById(String id) {
        long certificateId = parseId(id);
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(certificateId);
        if (optionalGiftCertificate.isPresent()) {
            return optionalGiftCertificate.get();
        } else {
            throw new GiftCertificateNotFoundException(messageSource.getMessage("error.gift.not.found",
                    new Object[]{id},
                    locale),
                    RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public long delete(String id) {
        long idValue = parseId(id);
        if (giftCertificateDao.delete(idValue)) {
            return idValue;
        } else {
            throw new GiftCertificateNotFoundException(messageSource.getMessage("error.gift.not.found",
                    new Object[]{id},
                    locale),
                    RestErrorStatusCode.ENTITY_NOT_FOUND);
        }
    }

    @Override
    public List<GiftCertificate> findByParameters(String tagName, String certificateValue,
                                                  String dateSort, String nameSort) {
        List<GiftCertificate> resultList = new ArrayList<>();
        if (tagName != null) {
            resultList = giftCertificateDao.findByTag(tagName);
        }
        if (certificateValue != null) {
            List<GiftCertificate> certificateList = giftCertificateDao.findByNameOrDescription(certificateValue);
            resultList = (resultList.isEmpty()) ? certificateList
                    : resultList.stream().filter(certificateList::contains).collect(Collectors.toList());
        }
        sortGiftCertificateList(resultList, dateSort, Comparator.comparing(GiftCertificate::getCreateDate));
        sortGiftCertificateList(resultList, nameSort, Comparator.comparing(GiftCertificate::getName));
        return resultList;
    }

    private void sortGiftCertificateList(List<GiftCertificate> resultList, String sortType,
                                         Comparator<GiftCertificate> comparing) {
        if (sortType != null) {
            if (sortType.equalsIgnoreCase(ASC_SORT)) {
                resultList.sort(comparing);
            } else if (sortType.equalsIgnoreCase(DESC_SORT)) {
                resultList.sort(comparing);
                Collections.reverse(resultList);
            } else {
                throw new ValidationException(messageSource.getMessage("error.gift.sort.type",
                        new Object[]{sortType},
                        locale),
                        RestErrorStatusCode.VALIDATION_ERROR);
            }
        }
    }
}
