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
        String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    ConfigurationContext configContext =
            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    BpsStatsDataPublisherAdminClient client = new BpsStatsDataPublisherAdminClient(
            cookie, backendServerURL, configContext, request.getLocale());
    PublishingConfigData publishingConfigData = null;
    String username = "pubudu";
    String password = "pubu password";
    String url = "pub url";
 
        try {
               
                publishingConfigData = new PublishingConfigData();
                publishingConfigData.setUserName("newuserpub");
                publishingConfigData.setUrl("urlnew");
                publishingConfigData.setPassword("newpaswed");
                publishingConfigData.setPublishingEnable(true);
                client.setPublishingConfigData(publishingConfigData);

               publishingConfigData = client.getPublishingConfigData();

            username = publishingConfigData.getUserName();
              password = publishingConfigData.getPassword();
            url = publishingConfigData.getUrl();
            
        } catch (Exception e) {
            CarbonUIMessage.sendCarbonUIMessage(e.getMessage(), CarbonUIMessage.ERROR, request, e);
%>
            <script type="text/javascript">
                   location.href = "../admin/error.jsp";
            </script>
<%
            return;
    }
%>
 
<div id="middle">
    <h2>Student Management</h2>
 
    <div id="workArea">
        <table class="styledLeft" id="moduleTable">
                <thead>
                <tr>
                    <th width="20%">ID</th>
                    <th width="40%">First Name</th>
                    <th width="40%">Last Name</th>
                </tr>
                </thead>
                <tr>
                    <td><%=username%></td>
                    <td><%=password%></td>
                    <td><%=url%></td>
                </tr>
                <tbody>
           
          
                </tbody>
         </table>
    </div>
</div>

</fmt:bundle>