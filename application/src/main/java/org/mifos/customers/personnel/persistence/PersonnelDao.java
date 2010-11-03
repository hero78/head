/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
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

package org.mifos.customers.personnel.persistence;

import java.util.Iterator;
import java.util.List;

import org.mifos.application.master.business.CustomFieldDefinitionEntity;
import org.mifos.application.servicefacade.CenterCreation;
import org.mifos.customers.personnel.business.PersonnelBO;
import org.mifos.customers.personnel.business.PersonnelCustomFieldEntity;
import org.mifos.customers.personnel.business.PersonnelDto;
import org.mifos.dto.domain.UserSearchDto;
import org.mifos.dto.screen.SystemUserSearchResultsDto;
import org.mifos.security.MifosUser;

public interface PersonnelDao {

    void save(PersonnelBO user);

    PersonnelBO findPersonnelById(Short id);

    PersonnelBO findPersonnelByUsername(String personnelName);

    PersonnelBO findByGlobalPersonnelNum(String globalNumber);

    MifosUser findAuthenticatedUserByUsername(String username);

    List<PersonnelDto> findActiveLoanOfficersForOffice(CenterCreation centerCreationDto);

    Iterator<CustomFieldDefinitionEntity> retrieveCustomFieldEntitiesForPersonnel();

    Iterator<PersonnelCustomFieldEntity> getCustomFieldResponses(Short customFieldId);

    SystemUserSearchResultsDto search(UserSearchDto searchDto, MifosUser user);
}