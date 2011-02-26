/*
 * Copyright (c) 2005-2011 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.application.messagecustomizer;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.mifos.application.admin.servicefacade.MessageCustomizerServiceFacade;
import org.mifos.dto.domain.AccountStatusesLabelDto;
import org.mifos.dto.domain.ConfigurableLookupLabelDto;
import org.mifos.dto.domain.GracePeriodDto;
import org.mifos.dto.domain.OfficeLevelDto;
import org.mifos.dto.screen.ConfigureApplicationLabelsDto;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 *
 */
public class MessageCustomizerServiceFacadeWebTier implements MessageCustomizerServiceFacade {

    private static final String HEAD_OFFICE_KEY = "datadisplayandrules.defineLabels.headoffice";
    private static final String REGIONAL_OFFICE_KEY = "datadisplayandrules.defineLabels.regionaloffice";
    private static final String SUB_REGIONAL_OFFICE_KEY = "datadisplayandrules.defineLabels.subregionaloffice";
    private static final String AREA_OFFICE_KEY = "datadisplayandrules.defineLabels.areaoffice";
    private static final String BRANCH_OFFICE_KEY = "datadisplayandrules.defineLabels.branchoffice";
    
    private static final String CLIENT_KEY = "datadisplayandrules.defineLabels.client";    
    private static final String GROUP_KEY = "datadisplayandrules.defineLabels.group";
    private static final String CENTER_KEY = "datadisplayandrules.defineLabels.center";
    
    private static final String LOANS_KEY = "datadisplayandrules.defineLabels.loans";
    private static final String SAVINGS_KEY = "datadisplayandrules.defineLabels.savings";
    
    private static final String STATE_KEY = "datadisplayandrules.defineLabels.state";
    private static final String POSTAL_CODE_KEY = "datadisplayandrules.defineLabels.postalcode";
    private static final String ETHNICITY_KEY = "datadisplayandrules.defineLabels.ethnicity";
    private static final String CITIZENSHIP_KEY = "datadisplayandrules.defineLabels.citizenship";
    private static final String HANDICAPPED_KEY = "datadisplayandrules.defineLabels.handicapped";
    private static final String GOVERNMENT_ID_KEY = "datadisplayandrules.defineLabels.governmentID";
    private static final String ADDRESS1_KEY = "datadisplayandrules.defineLabels.address1";
    private static final String ADDRESS2_KEY = "datadisplayandrules.defineLabels.address2";
    private static final String ADDRESS3_KEY = "datadisplayandrules.defineLabels.address3";
    
    private static final String PARTIAL_APPLICATION_KEY = "datadisplayandrules.defineLabels.partialApplication";
    private static final String PENDING_APPROVAL_KEY = "datadisplayandrules.defineLabels.pendingApproval";
    private static final String APPROVED_KEY = "datadisplayandrules.defineLabels.approved";
    private static final String CANCEL_KEY = "datadisplayandrules.defineLabels.cancel";
    private static final String CLOSED_KEY = "datadisplayandrules.defineLabels.closed";
    private static final String ON_HOLD_KEY = "datadisplayandrules.defineLabels.onhold";
    private static final String ACTIVE_KEY = "datadisplayandrules.defineLabels.active";
    private static final String INACTIVE_KEY = "datadisplayandrules.defineLabels.inactive";
    private static final String ACTIVE_IN_GOOD_STANDING_KEY = "datadisplayandrules.defineLabels.activeingoodstanding";
    private static final String ACTIVE_IN_BAD_STANDING_KEY = "datadisplayandrules.defineLabels.activeinbadstanding";
    private static final String CLOSED_OBLIGATION_MET_KEY = "datadisplayandrules.defineLabels.closed-obligationmet";
    private static final String CLOSED_RESCHEDULED_KEY = "datadisplayandrules.defineLabels.closed-rescheduled";
    private static final String CLOSED_WRITTEN_OFF_KEY = "datadisplayandrules.defineLabels.closed-writtenoff";
    
    private static final String NONE_KEY = "datadisplayandrules.defineLabels.none";
    private static final String GRACE_ON_ALL_REPAYMENTS_KEY = "datadisplayandrules.defineLabels.graceonallrepayments";
    private static final String PRINCIPAL_ONLY_GRACE_KEY = "datadisplayandrules.defineLabels.principalonlygrace";
    
    private static final String INTEREST_KEY = "datadisplayandrules.defineLabels.interest";
    private static final String EXTERNAL_ID_KEY = "datadisplayandrules.defineLabels.externalID";
    private static final String BULK_ENTRY_KEY = "datadisplayandrules.defineLabels.bulkentry";

//	@Autowired
//	private MessageCustomizerDao messageCustomizerDao;
	
	@Autowired
	private MessageSource messageSource;
		
    private LinkedHashMap<String,String> messageFilterMap = new LinkedHashMap<String,String>();
    	
    @Autowired
	public MessageCustomizerServiceFacadeWebTier(
//			MessageCustomizerDao messageCustomizerDao,
			MessageSource messageSource) {
		super();
//		this.messageCustomizerDao = messageCustomizerDao;
		this.messageSource = messageSource;
	}

	@Override
	public ConfigureApplicationLabelsDto retrieveConfigurableLabels(Locale locale) {
		OfficeLevelDto officeLevels = populateOffices(locale);
		GracePeriodDto gracePeriodDto = populateGrace(locale);
		ConfigurableLookupLabelDto lookupLabels = populateLookupLabels(locale);
		AccountStatusesLabelDto accountStatusLabels = populateAccountStatuses(locale);

		return new ConfigureApplicationLabelsDto(officeLevels, gracePeriodDto, lookupLabels, accountStatusLabels);
	}

	@Override
	public void updateApplicationLabels(Map<String,String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) { 
        	messageFilterMap.put(entry.getKey(), entry.getValue());
        }
	}
	
    @Override
    public String replaceSubstitutions(String message) {
    	if (message == null) return message;
    	
    	if (message.startsWith("|||")) {
    		return message.substring(3);
    	}
    	
    	String newMessage = message;
    	
        for (Map.Entry<String, String> entry : messageFilterMap.entrySet()) { 
        	newMessage = newMessage.replace(entry.getKey(), entry.getValue());
        }
    	return newMessage;    	
    }

    private GracePeriodDto populateGrace(Locale locale) {
    	GracePeriodDto grace = new GracePeriodDto();
    	String message = null;
    	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(NONE_KEY, null, locale)));
    	grace.setNone(message);
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(GRACE_ON_ALL_REPAYMENTS_KEY, null, locale)));
    	grace.setGraceOnAllRepayments(message);
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(PRINCIPAL_ONLY_GRACE_KEY, null, locale)));
    	grace.setPrincipalOnlyGrace(message);
    	
    	return grace;
    }
    private OfficeLevelDto populateOffices(Locale locale) {
    	OfficeLevelDto offices = new OfficeLevelDto();
    	offices.setHeadOfficeEnabled(true);
    	offices.setRegionalOfficeEnabled(true);
    	offices.setSubRegionalOfficeEnabled(true);
    	offices.setAreaOfficeEnabled(true);
    	offices.setBranchOfficeEnabled(true);
    	
    	String message = null;

    	message = replaceSubstitutions(stripColon(messageSource.getMessage(HEAD_OFFICE_KEY, null, locale)));
    	offices.setHeadOfficeNameKey(message);
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(REGIONAL_OFFICE_KEY, null, locale)));
    	offices.setRegionalOfficeNameKey(message);    	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(SUB_REGIONAL_OFFICE_KEY, null, locale)));
    	offices.setSubRegionalOfficeNameKey(message);    	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(AREA_OFFICE_KEY, null, locale)));
    	offices.setAreaOfficeNameKey(message);    	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(BRANCH_OFFICE_KEY, null, locale)));
    	offices.setBranchOfficeNameKey(message);   
    	
    	return offices;
    }
    
    private AccountStatusesLabelDto  populateAccountStatuses(Locale locale) {
    	AccountStatusesLabelDto statuses = new AccountStatusesLabelDto();
    	String message = null;
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(PARTIAL_APPLICATION_KEY, null, locale)));
    	statuses.setPartialApplication(getCustomMessageFor(message));		
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(PENDING_APPROVAL_KEY, null, locale)));
    	statuses.setPendingApproval(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(APPROVED_KEY, null, locale)));
    	statuses.setApproved(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(CANCEL_KEY, null, locale)));
    	statuses.setCancel(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(CLOSED_KEY, null, locale)));
    	statuses.setClosed(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(ON_HOLD_KEY, null, locale)));
    	statuses.setOnhold(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(ACTIVE_KEY, null, locale)));
    	statuses.setActive(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(INACTIVE_KEY, null, locale)));
    	statuses.setInActive(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(ACTIVE_IN_GOOD_STANDING_KEY, null, locale)));
    	statuses.setActiveInGoodStanding(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(ACTIVE_IN_BAD_STANDING_KEY, null, locale)));
    	statuses.setActiveInBadStanding(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(CLOSED_OBLIGATION_MET_KEY, null, locale)));
    	statuses.setClosedObligationMet(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(CLOSED_RESCHEDULED_KEY, null, locale)));
    	statuses.setClosedRescheduled(getCustomMessageFor(message));	
    	message = replaceSubstitutions(stripColon(messageSource.getMessage(CLOSED_WRITTEN_OFF_KEY, null, locale)));
    	statuses.setClosedWrittenOff(getCustomMessageFor(message));	

    	return statuses;
    }
    
    private String stripColon(String message) {
    	return message.substring(0, message.length()-1);
    }
    
	private ConfigurableLookupLabelDto populateLookupLabels(Locale locale) {
		ConfigurableLookupLabelDto lookupLabels = new ConfigurableLookupLabelDto();
		
		String message = null;
		message = replaceSubstitutions(stripColon(messageSource.getMessage(CLIENT_KEY, null, locale)));
		lookupLabels.setClient(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(GROUP_KEY, null, locale)));
		lookupLabels.setGroup(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(CENTER_KEY, null, locale)));
		lookupLabels.setCenter(getCustomMessageFor(message));

		message = replaceSubstitutions(stripColon(messageSource.getMessage(LOANS_KEY, null, locale)));
		lookupLabels.setLoans(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(SAVINGS_KEY, null, locale)));
		lookupLabels.setSavings(getCustomMessageFor(message));
		
		message = replaceSubstitutions(stripColon(messageSource.getMessage(STATE_KEY, null, locale)));
		lookupLabels.setState(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(POSTAL_CODE_KEY, null, locale)));
		lookupLabels.setPostalCode(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(ETHNICITY_KEY, null, locale)));
		lookupLabels.setEthnicity(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(CITIZENSHIP_KEY, null, locale)));
		lookupLabels.setCitizenship(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(HANDICAPPED_KEY, null, locale)));
		lookupLabels.setHandicapped(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(GOVERNMENT_ID_KEY, null, locale)));
		lookupLabels.setGovtId(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(ADDRESS1_KEY, null, locale)));
		lookupLabels.setAddress1(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(ADDRESS2_KEY, null, locale)));
		lookupLabels.setAddress2(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(ADDRESS3_KEY, null, locale)));
		lookupLabels.setAddress3(getCustomMessageFor(message));

		message = replaceSubstitutions(stripColon(messageSource.getMessage(INTEREST_KEY, null, locale)));
		lookupLabels.setInterest(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(EXTERNAL_ID_KEY, null, locale)));
		lookupLabels.setExternalId(getCustomMessageFor(message));
		message = replaceSubstitutions(stripColon(messageSource.getMessage(BULK_ENTRY_KEY, null, locale)));
		lookupLabels.setBulkEntry(getCustomMessageFor(message));
		return lookupLabels;
	}

	private String getCustomMessageFor(String message) {
		String customMessage = messageFilterMap.get(message);
		if (customMessage == null) {
			return message;
		}
		return customMessage;
	}

	@Override
	public void updateApplicationLabels(
			ConfigureApplicationLabelsDto applicationLabels, Locale locale) {
		ConfigureApplicationLabelsDto originalLabelsDto = retrieveConfigurableLabels(locale);
		
		LinkedHashMap<String,String> updateMap = new LinkedHashMap<String, String>();
		
		updateLookupLabels(applicationLabels.getLookupLabels(), locale, originalLabelsDto.getLookupLabels(), updateMap);	
		updateAccountStatuses(applicationLabels.getAccountStatusLabels(), locale, originalLabelsDto.getAccountStatusLabels(), updateMap);	
		updateOffices(applicationLabels.getOfficeLevels(), locale, originalLabelsDto.getOfficeLevels(), updateMap);	
		updateGrace(applicationLabels.getGracePeriodDto(), locale, originalLabelsDto.getGracePeriodDto(), updateMap);
		
		updateApplicationLabels(updateMap);
	}


	private void updateGrace(GracePeriodDto newGracePeriodDto, Locale locale,
			GracePeriodDto oldgracePeriodDto,
			LinkedHashMap<String, String> updateMap) {

		updateLabel(oldgracePeriodDto.getNone(), newGracePeriodDto.getNone(), NONE_KEY, locale, updateMap);
		updateLabel(oldgracePeriodDto.getGraceOnAllRepayments(), newGracePeriodDto.getGraceOnAllRepayments(), GRACE_ON_ALL_REPAYMENTS_KEY, locale, updateMap);
		updateLabel(oldgracePeriodDto.getPrincipalOnlyGrace(), newGracePeriodDto.getPrincipalOnlyGrace(), PRINCIPAL_ONLY_GRACE_KEY, locale, updateMap);
		
	}

	private void updateOffices(OfficeLevelDto newOfficeLevels, Locale locale,
			OfficeLevelDto OldOfficeLevels,
			LinkedHashMap<String, String> updateMap) {
		updateLabel(OldOfficeLevels.getHeadOfficeNameKey(), newOfficeLevels.getHeadOfficeNameKey(), HEAD_OFFICE_KEY, locale, updateMap);
		updateLabel(OldOfficeLevels.getRegionalOfficeNameKey(), newOfficeLevels.getRegionalOfficeNameKey(), REGIONAL_OFFICE_KEY, locale, updateMap);
		updateLabel(OldOfficeLevels.getSubRegionalOfficeNameKey(), newOfficeLevels.getSubRegionalOfficeNameKey(), SUB_REGIONAL_OFFICE_KEY, locale, updateMap);
		updateLabel(OldOfficeLevels.getAreaOfficeNameKey(), newOfficeLevels.getAreaOfficeNameKey(), AREA_OFFICE_KEY, locale, updateMap);
		updateLabel(OldOfficeLevels.getBranchOfficeNameKey(), newOfficeLevels.getBranchOfficeNameKey(), BRANCH_OFFICE_KEY, locale, updateMap);				
	}

	private void updateAccountStatuses(AccountStatusesLabelDto newLabels,
			Locale locale, AccountStatusesLabelDto oldLabels,
			LinkedHashMap<String, String> updateMap) {
		updateLabel(oldLabels.getPartialApplication(), newLabels.getPartialApplication(), PARTIAL_APPLICATION_KEY, locale, updateMap);
		updateLabel(oldLabels.getPendingApproval(), newLabels.getPendingApproval(), PENDING_APPROVAL_KEY, locale, updateMap);
		updateLabel(oldLabels.getApproved(), newLabels.getApproved(), APPROVED_KEY, locale, updateMap);
		updateLabel(oldLabels.getCancel(), newLabels.getCancel(), CANCEL_KEY, locale, updateMap);
		updateLabel(oldLabels.getClosed(), newLabels.getClosed(), CLOSED_KEY, locale, updateMap);
		updateLabel(oldLabels.getOnhold(), newLabels.getOnhold(), ON_HOLD_KEY, locale, updateMap);
		updateLabel(oldLabels.getActive(), newLabels.getActive(), ACTIVE_KEY, locale, updateMap);
		updateLabel(oldLabels.getInActive(), newLabels.getInActive(), INACTIVE_KEY, locale, updateMap);
		updateLabel(oldLabels.getActiveInGoodStanding(), newLabels.getActiveInGoodStanding(), ACTIVE_IN_GOOD_STANDING_KEY, locale, updateMap);
		updateLabel(oldLabels.getActiveInBadStanding(), newLabels.getActiveInBadStanding(), ACTIVE_IN_BAD_STANDING_KEY, locale, updateMap);
		updateLabel(oldLabels.getClosedObligationMet(), newLabels.getClosedObligationMet(), CLOSED_OBLIGATION_MET_KEY, locale, updateMap);
		updateLabel(oldLabels.getClosedRescheduled(), newLabels.getClosedRescheduled(), CLOSED_RESCHEDULED_KEY, locale, updateMap);
		updateLabel(oldLabels.getClosedWrittenOff(), newLabels.getClosedWrittenOff(), CLOSED_WRITTEN_OFF_KEY, locale, updateMap);
	}
	
	private void updateLookupLabels(ConfigurableLookupLabelDto newLookupLabelDto,
			Locale locale, ConfigurableLookupLabelDto oldLookupLabelDto,
			LinkedHashMap<String, String> updateMap) {
		updateLabel(oldLookupLabelDto.getClient(), newLookupLabelDto.getClient(), CLIENT_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getGroup(), newLookupLabelDto.getGroup(), GROUP_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getCenter(), newLookupLabelDto.getCenter(), CENTER_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getLoans(), newLookupLabelDto.getLoans(), LOANS_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getSavings(), newLookupLabelDto.getSavings(), SAVINGS_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getState(), newLookupLabelDto.getState(), STATE_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getPostalCode(), newLookupLabelDto.getPostalCode(), POSTAL_CODE_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getEthnicity(), newLookupLabelDto.getEthnicity(), ETHNICITY_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getCitizenship(), newLookupLabelDto.getCitizenship(), CITIZENSHIP_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getHandicapped(), newLookupLabelDto.getHandicapped(), HANDICAPPED_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getGovtId(), newLookupLabelDto.getGovtId(), GOVERNMENT_ID_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getAddress1(), newLookupLabelDto.getAddress1(), ADDRESS1_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getAddress2(), newLookupLabelDto.getAddress2(), ADDRESS2_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getAddress3(), newLookupLabelDto.getAddress3(), ADDRESS3_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getInterest(), newLookupLabelDto.getInterest(), INTEREST_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getExternalId(), newLookupLabelDto.getExternalId(), EXTERNAL_ID_KEY, locale, updateMap);
		updateLabel(oldLookupLabelDto.getBulkEntry(), newLookupLabelDto.getBulkEntry(), BULK_ENTRY_KEY, locale, updateMap);
	}


	private void updateLabel(String oldMessage, String newMessage, String key, Locale locale, LinkedHashMap<String, String> updateMap) {
		if (!oldMessage.contentEquals(newMessage)) {
			updateMap.put(stripColon(messageSource.getMessage(key, null, locale)), newMessage);
		}		
	}

}