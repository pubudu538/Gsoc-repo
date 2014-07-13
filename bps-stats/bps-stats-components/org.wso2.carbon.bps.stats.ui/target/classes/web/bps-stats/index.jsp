<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.bps.stats.conf.xsd.PublishingConfigData" %>
<%@ page import="org.wso2.carbon.bps.stats.ui.BpsStatsDataPublisherAdminClient" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<fmt:bundle basename="org.wso2.carbon.bps.stats.ui.i18n.Resources">

<carbon:breadcrumb
        label="bam.statistics"
        resourceBundle="org.wso2.carbon.bps.stats.ui.i18n.Resources"
        topPage="true"
        request="<%=request%>"/>


<%
    

    String setConfig = request.getParameter("setConfig"); // hidden parameter to check if the form is being submitted
    String enableServiceStats = request.getParameter("enableServiceStats"); // String value is "on" of checkbox clicked, else null
    String url = request.getParameter("bam_url");
    String userName = request.getParameter("bam_username");
    String password = request.getParameter("bam_password");

   

    String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    ConfigurationContext configContext =
            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    BpsStatsDataPublisherAdminClient client = new BpsStatsDataPublisherAdminClient(
            cookie, backendServerURL, configContext, request.getLocale());
    PublishingConfigData publishingConfigData = null;
  

    if (setConfig != null) { // form submitted request to set eventing config
        publishingConfigData = new PublishingConfigData();
      

        if (enableServiceStats != null) {
             publishingConfigData.setPublishingEnable(true);
        } else {
           publishingConfigData.setPublishingEnable(false);
        }
        
        if (url != null) {
            publishingConfigData.setUrl(url);
        }
        if (userName != null) {
            publishingConfigData.setUserName(userName);
        }
        if (password != null) {
            publishingConfigData.setPassword(password);
        }

        
        try {
            client.setPublishingConfigData(publishingConfigData);


%>

<script type="text/javascript">    
        function handleOK() {
        }

        CARBON.showInfoDialog("BPS Statistics Publishing Configuration Successfully Updated!", handleOK);
    
</script>

<%
} catch (Exception e) {
    if (e.getCause().getMessage().toLowerCase().indexOf("you are not authorized") == -1) {
        response.setStatus(500);
        CarbonUIMessage uiMsg = new CarbonUIMessage(CarbonUIMessage.ERROR, e.getMessage(), e);
        session.setAttribute(CarbonUIMessage.ID, uiMsg);
%>

<jsp:include page="../admin/error.jsp"/>

<%
        }
    }
} else {
    try {
        publishingConfigData = client.getPublishingConfigData();
    } catch (Exception e) {
        if (e.getCause().getMessage().toLowerCase().indexOf("you are not authorized") == -1) {
            response.setStatus(500);
            CarbonUIMessage uiMsg = new CarbonUIMessage(CarbonUIMessage.ERROR, e.getMessage(), e);
            session.setAttribute(CarbonUIMessage.ID, uiMsg);
%>

<jsp:include page="../admin/error.jsp"/>

<%
            }
        }
    }


    boolean isServiceStatsEnable = publishingConfigData.getPublishingEnable();

    

    if (url == null) {
        url = publishingConfigData.getUrl();
    }
    if (userName == null) {
        userName = publishingConfigData.getUserName();
    }
    if (password == null) {
        password = publishingConfigData.getPassword();
    }

    
%>



 
<script id="source" type="text/javascript">

 $(document).ready(function(){
        if($(".serviceConfigurationCheckBox").is(":checked")){
                $(".serviceConfigurationInput").each(function(){
                    $(this).removeAttr('readonly');
                });
           }else{
                $(".serviceConfigurationInput").each(function(){
                    $(this).attr('readonly','readonly');
                })
        }
       
    });

    function enableStatisticsStreamFields(){
           if($(".serviceConfigurationCheckBox").is(":checked")){
                $(".serviceConfigurationInput").each(function(){
                    $(this).removeAttr('readonly');
                });
           }else{
                $(".serviceConfigurationInput").each(function(){
                    $(this).attr('readonly','readonly');
                })
           }
    }


</script>


<div id="middle">
    <h2><fmt:message key="bps.stats.config.heading"/></h2>

 
    <div id="workArea">
        <div id="result"></div>
        <p>&nbsp;</p>

         <form action="index.jsp" method="post">
            <input type="hidden" name="setConfig" value="on"/>
            <table width="100%" class="styledLeft" style="margin-left: 0px;">
                <thead>
                <tr>
                    <th colspan="4">
                        <fmt:message key="singlenode.configuration"/>
                    </th>
                </tr>
                </thead>
                <tr>
                    <td>
                        <% if (isServiceStatsEnable) { %>
                        <input type="checkbox" name="enableServiceStats"
                               checked="true" class="serviceConfigurationCheckBox" onchange="enableStatisticsStreamFields()">&nbsp;&nbsp;&nbsp;&nbsp;
                        <% } else { %>
                        <input type="checkbox" name="enableServiceStats" class="serviceConfigurationCheckBox" onchange="enableStatisticsStreamFields()">&nbsp;&nbsp;&nbsp;&nbsp;
                        <% } %>
                        <fmt:message key="enable.service.stats"/>

                    </td>
                </tr>
                    <tr>
                        <td><fmt:message key="bam.url"/></td>
                        <td><input id="bam_url" class="serviceConfigurationInput" type="text" name="bam_url" value="<%=url%>"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="bam.username"/></td>
                        <td><input id="bam_username" type="text" class="serviceConfigurationInput" name="bam_username" value="<%=userName%>"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="bam.password"/></td>
                        <td><input id="bam_password" type="text" class="serviceConfigurationInput" name="bam_password" value="<%=password%>"/></td>
                    </tr>

                    <tr>
                    <td colspan="4" class="buttonRow">
                   
                        <input type="submit" class="button" value="<fmt:message key="update"/>"
                               id="updateStats"/>&nbsp;&nbsp;                                        
                    
                    </td>
                     </tr>





                 </table>
                </form>


    </div>
</div>

</fmt:bundle>