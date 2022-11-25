//package com.aem.geeks.core.services;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.jcr.Session;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.jackrabbit.api.security.user.Authorizable;
//import org.apache.jackrabbit.api.security.user.UserManager;
//import org.apache.sling.api.resource.LoginException;
//
//
//import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.resource.ResourceResolverFactory;
//
//import org.apache.sling.auth.core.spi.AuthenticationInfo;
//import org.apache.sling.auth.core.spi.AuthenticationInfoPostProcessor;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Component(name = "UserProfileService", service = AuthenticationInfoPostProcessor.class, immediate = true)
//
//public class UserProfileService implements AuthenticationInfoPostProcessor {
//
//    /**
//     * This class generate Last login property of any user profile
//     *
//     * @param authenticationinfo
//     * @param servletrequest
//     * @param servletresponse
//     */
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileService.class);
//    @Reference
//    private ResourceResolverFactory resourceResolverFactory;
//
//    @Override
//    public void postProcess(AuthenticationInfo info, HttpServletRequest request, HttpServletResponse response)
//            throws LoginException {
//
//    /**
//         * Users last logged in will be his last active time in AEM
//         * Executed only when it is a logout operation to ensure the last active time is captured
//         * Ensure to update the code with relevant condition
//         */
//        if ((info != null && info.getAuthType() == null) || (request != null && request.getServletPath() != null
//                && (!request.getServletPath().equals("/system/sling/logout.html")))) {
//            LOGGER.debug("AuthenticationInfo is null. " + "we can skip post processing this request.");
//            return;
//        }
//
//        ResourceResolver resourceResolver = null;
//        Session session = null;
//        UserManager userManager = null;
//        Authorizable auth = null;
//
//        try {
//            resourceResolver = resourceResolverFactory.getResourceResolver(info);
//            session = resourceResolver.adaptTo(Session.class);
//            userManager = resourceResolver.adaptTo(UserManager.class);
//            auth = userManager.getAuthorizable(session.getUserID());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//                //Anonymous users need not be checked
//                if (auth.getID() != null && (!auth.getID().equals("anonymous"))) {
//                    LOGGER.info("Logged in Users log in");
//                    //Profile will have a new property
//                    auth.setProperty("profile/lastLoggedIn", session.getValueFactory().createValue(sdf.format(new Date())));
//                    session.save();
//                    session.logout();
//                }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//}