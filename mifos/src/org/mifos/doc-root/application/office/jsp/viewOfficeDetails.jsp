<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/tags/mifos-html" prefix="mifos"%>
<%@taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insert definition=".view">
	<tiles:put name="body" type="string">
		<script language="javascript">
function goToCancelPage(){
	document.offActionForm.method.value="get";
	offActionForm.submit();
  }
 function submitViewOfficesLink(){
	document.offActionForm.method.value="loadall";
	document.offActionForm.action="OfficeAction.do";	
		
	offActionForm.submit();
  } 
  function editOfficeInformationLink(){
	document.offActionForm.method.value="manage";
	document.offActionForm.input.value="manage";	
	offActionForm.submit();
  }  
  function  submitAdminLink()
{
		document.offActionForm.method.value="load";
		document.offActionForm.action="AdminAction.do";
		offActionForm.submit();
}
</script>

		<html-el:form action="/offAction.do" >

			
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="bluetablehead05"><span class="fontnormal8pt"> 
							<html-el:link href="javascript:submitAdminLink()"><mifos:mifoslabel
								name="Office.labelLinkAdmin" /></html-el:link> / <html-el:link
										href="javascript:submitViewOfficesLink()" > <mifos:mifoslabel
								name="Office.labelLinkViewOffices" />

							</html-el:link> / </span><span class="fontnormal8ptbold"><c:out
								value="${BusinessKey.officeName}"></c:out></span></td>
						</tr>
					</table>
					<table width="95%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="left" valign="top" class="paddingL15T15">
							<table width="96%" border="0" cellpadding="3" cellspacing="0">
								<tr>
									<td width="50%" height="23" class="headingorange"><c:out
										value="${BusinessKey.officeName}"></c:out></td>
									<td width="50%" align="right">
									<html-el:link href="javascript:editOfficeInformationLink()"
										>
										<mifos:mifoslabel name="Office.labelEditOfficeInfo"
											/>
									</html-el:link>
									</td>
								</tr>
								 <tr><td colspan="2"><font class="fontnormalRedBold"><html-el:errors
								bundle="OfficeUIResources" /> </font>
								</td></tr>
								<tr>
									<td height="23" class="fontnormalbold"><span class="fontnormal">
									</span>
									<span class="fontnormal"> 
									<c:if test="${BusinessKey.officeStatus == OfficeStatus.ACTIVE}">
									<mifos:MifosImage id="active" moduleName="office" />
									</c:if>
									<c:if test="${BusinessKey.officeStatus == OfficeStatus.INACTIVE}">
									<mifos:MifosImage id="inactive" moduleName="office" />
									</c:if>
									<c:out value="${BusinessKey.status.name}"></c:out>
									</span> 
									
									<br>
									<span class="fontnormal"><mifos:mifoslabel
										name="Office.labelOfficeShortName" />
									<c:out value="${BusinessKey.shortName}"></c:out><br>
									 <!-- End Logic for showing the correct code--> 
									<mifos:mifoslabel name="Office.labelOfficeType"/>
									<c:out value="${BusinessKey.level.name}"></c:out>
									 <br>
									<!-- logic for showing the correct parent -->
									<mifos:mifoslabel name="Office.labelParentOffice"	/> 
										<c:if test="${not empty BusinessKey.parentOffice}"> 
										<c:out value="${BusinessKey.parentOffice.officeName}"></c:out>
										</c:if>
										<br>
										
									 </span><br>
									 <!-- End for showing the correct parent -->
									<span class="fontnormal"></span><span class="fontnormal"> </span><span
										class="fontnormal"></span><br>
										
									<c:if test="${not empty BusinessKey.address.address.phoneNumber ||
									 not empty BusinessKey.address.address.line1 ||
									 not empty BusinessKey.address.address.line2 ||
									 not empty BusinessKey.address.address.line3 ||
									 not empty BusinessKey.address.address.city	 ||
									 not empty BusinessKey.address.address.state	 ||
									 not empty BusinessKey.address.address.country	 ||
									 not empty BusinessKey.address.address.zip }">	
									<mifos:mifoslabel name="office.labelAddress"
										bundle="OfficeResources"></mifos:mifoslabel>
									
									<c:if 	test="${not empty BusinessKey.address.address.line1 ||
									 not empty BusinessKey.address.address.line2 ||
									 not empty BusinessKey.address.address.line3 }">
									 <br>
									<span
										class="fontnormal"><c:out
										value="${BusinessKey.address.address.line1}"></c:out>
										<c:if
										test="${not empty BusinessKey.address.address.line1 &&(not empty BusinessKey.address.address.line2||not empty BusinessKey.address.address.line3)}">, </c:if><c:if
										test="${not empty BusinessKey.address.address.line2}">${BusinessKey.address.address.line2}</c:if><c:if
										test="${not empty BusinessKey.address.address.line3&&not empty BusinessKey.address.address.line2}">, </c:if><c:if
										test="${not empty BusinessKey.address.address.line3}">${BusinessKey.address.address.line3}</c:if>
									</span>
									</c:if>
									
									<c:if test="${not empty BusinessKey.address.address.city}">
									<br>
									<span class="fontnormal"><c:out
										value="${BusinessKey.address.address.city}"></c:out>
									</span> 
									</c:if>
									<c:if test="${not empty BusinessKey.address.address.state}">
									<br>
									<span class="fontnormal"><c:out
										value="${BusinessKey.address.address.state}"></c:out>
									</span></c:if>
									
									<c:if test="${not empty BusinessKey.address.address.country}">
									<br>
									<span class="fontnormal"><c:out
										value="${BusinessKey.address.address.country}"></c:out> 
										</span>
									</c:if>
									<c:if test="${not empty BusinessKey.address.address.zip}">
									<br>
									<span class="fontnormal"> <c:out
										value="${BusinessKey.address.address.zip}"></c:out>
									</span>
									</c:if>
									<!-- bug 26503  -->
									<c:if test="${ not empty BusinessKey.address.address.phoneNumber}">
									<br>
									<span
										class="fontnormal">
									<mifos:mifoslabel name="Office.labelTelephone"
										/></span> <span
										class="fontnormal"><c:out
										value="${BusinessKey.address.address.phoneNumber}"></c:out>
									</span>
									</c:if>
									</c:if>
									
									<br>
									
									
									
									<c:if test="${!empty BusinessKey.customFields}">
										<mifos:mifoslabel name="Office.labelAdditionInformation" />
									 <span class="fontnormal"><br>
									 <c:forEach var="cfdef"
										items="${BusinessKey.customFields}">
										<c:forEach var="cf" items="${sessionScope.customFields}">
											<c:if test="${cfdef.fieldId==cf.fieldId}">
											
												<font class="fontnormalBold"> 
												
												<mifos:mifoslabel
											name="${cf.lookUpEntity.entityType}"
											bundle="OfficeResources"></mifos:mifoslabel>
												:
												</font>
												<span class="fontnormal"><c:out value="${cfdef.fieldValue}" /><br>
												</span>
											</c:if>
										</c:forEach>
									</c:forEach>	
									</c:if>								</td>
								</tr>
							</table>
							<br>
							</td>
						</tr>
					</table>
					<br>
			<html-el:hidden	property="officeId"	value="${requestScope.OfficeVo.officeId}" />
			<html-el:hidden	property="method"	value="" />
		</html-el:form>

	</tiles:put>

</tiles:insert>
