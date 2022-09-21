package com.example.kibo;

import com.example.model.Context;
import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.MozuConfig;
import com.mozu.api.contracts.appdev.AppAuthInfo;
import com.mozu.api.security.AppAuthenticator;
import com.thoughtworks.xstream.InitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KiboApiContextManager implements InitializingBean {

    private String kiboApplicationKey = System.getenv("kiboApplicationKey");

    private String kiboApplicationSecret = System.getenv("kiboApplicationSecret");

    private String baseAppAuthUrl = System.getenv("baseAppAuthUrl");

    private String defaultEventRequestTimeout = System.getenv("defaultEventRequestTimeout");

    private final List<KiboApiContextExpireListener> kiboApiContextExpireListenerCache = new ArrayList<KiboApiContextExpireListener>();
    private Map<Context, ApiContext> kiboApiContextCache = new HashMap<>();
    private TaskScheduler taskScheduler;
    String appAuthTicket;

    @Override
    public void afterPropertiesSet() throws Exception {
        taskScheduler = new ConcurrentTaskScheduler();
        initializeNameSpaceAndAppAuth();
    }

    public KiboApiContentManagerBuilder newBuilder() {
        KiboApiContentManagerBuilder builder = new KiboApiContentManagerBuilder();
        return builder;
    }

    public class KiboApiContentManagerBuilder {
        private Context context;
        private KiboApiContextExpireListener kiboApiContextExpireListener;

        public ApiContext build() throws InitializationException {
            if (this.context == null || this.kiboApiContextExpireListener == null) {
                //throw new InitializationException(Error.KIBO_API_CONTEXT_BUILD_FAILED);
            }
            ApiContext apiContext = null;
            apiContext = getCahcedOrBuildApiContext(context, kiboApiContextExpireListener);
            return apiContext;
        }

        private ApiContext getCahcedOrBuildApiContext(Context context,
                KiboApiContextExpireListener kiboApiContextExpireListener) throws InitializationException {
            ApiContext apiContext = null;
            if ((apiContext = kiboApiContextCache.get(context)) == null) {
                synchronized (kiboApiContextCache) {
                    if ((apiContext = kiboApiContextCache.get(context)) == null) {
                        apiContext = new MozuApiContext(context.getTenantId(), context.getSiteId());
                        apiContext.setAppAuthTicket(appAuthTicket);
                        kiboApiContextCache.put(context, apiContext);
                    }
                }
                synchronized (kiboApiContextExpireListener) {
                    kiboApiContextExpireListenerCache.add(kiboApiContextExpireListener);
                }
            }
            return apiContext;
        }

        public KiboApiContentManagerBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public KiboApiContentManagerBuilder setKiboApiContextExpireListner(
                KiboApiContextExpireListener kiboApiContextExpireListener) {
            this.kiboApiContextExpireListener = kiboApiContextExpireListener;
            return this;
        }
    }

    public ApiContext getApiContext(Context context) {
        ApiContext apiContext = kiboApiContextCache.get(context);
        return apiContext;
    }

    private void initializeNameSpaceAndAppAuth() throws InitializationException {
        AppAuthInfo appAuthInfo = createAppAuthInfo();
        AppAuthenticator.initialize(appAuthInfo);
        AppAuthenticator appAuthenticator = AppAuthenticator.getInstance();
        appAuthTicket = appAuthenticator.getAppAuthTicket().getAccessToken();
       // String appId = appAuthenticator.getAppAuthInfo().getApplicationId();
       // KiboApiContextManager.nameSpace = appId.substring(0, appId.indexOf('.'));
        scheduleCacheRefresh(appAuthenticator);
    }

    private AppAuthInfo createAppAuthInfo() {
        AppAuthInfo appAuthInfo = new AppAuthInfo();
        appAuthInfo.setApplicationId(kiboApplicationKey);
        appAuthInfo.setSharedSecret(kiboApplicationSecret);

        if (baseAppAuthUrl != null && !baseAppAuthUrl.trim().equals(""))
            MozuConfig.setBaseUrl(baseAppAuthUrl);

        try {
            if (defaultEventRequestTimeout != null && !defaultEventRequestTimeout.trim().equals(""))
                MozuConfig.setDefaultEventRequestTimeout(Integer.parseInt(defaultEventRequestTimeout));
        } catch (Exception e) {
            MozuConfig.setDefaultEventRequestTimeout(10000);
        }
        return appAuthInfo;
    }

    private void scheduleCacheRefresh(AppAuthenticator appAuthenticator) {
        taskScheduler.schedule(new Runnable() {
            boolean successful = false;

            public void run() {
                while (!successful) {
                    try {
                        appAuthenticator.refreshAppAuthTicket();
                        initializeNameSpaceAndAppAuth();
                        cleanupCachedResources();
                        successful = true;
                    } catch (Exception e) {
                        try {
                            appAuthenticator.authenticateApp();
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        }, appAuthenticator.getAppAuthTicket().getAccessTokenExpiration().toDate());
    }

    private void cleanupCachedResources() {
        synchronized (kiboApiContextCache) {
            kiboApiContextCache.clear();
        }
        synchronized (kiboApiContextExpireListenerCache) {
            for (KiboApiContextExpireListener contextExpireListener : kiboApiContextExpireListenerCache) {
                contextExpireListener.contextExpired();
            }
            kiboApiContextExpireListenerCache.clear();
        }
    }

    public String getKiboApplicaionKey() {
        return kiboApplicationKey;
    }

    public void setKiboApplicaionKey(String kiboApplicaionKey) {
        this.kiboApplicationKey = kiboApplicaionKey;
    }

    public String getKiboApplicationSecret() {
        return kiboApplicationSecret;
    }

    public void setKiboApplicationSecret(String kiboApplicationSecret) {
        this.kiboApplicationSecret = kiboApplicationSecret;
    }

    public String getBaseAppAuthUrl() {
        return baseAppAuthUrl;
    }

    public void setBaseAppAuthUrl(String baseAppAuthUrl) {
        this.baseAppAuthUrl = baseAppAuthUrl;
    }

    public String getDefaultEventRequestTimeout() {
        return defaultEventRequestTimeout;
    }

    public void setDefaultEventRequestTimeout(String defaultEventRequestTimeout) {
        this.defaultEventRequestTimeout = defaultEventRequestTimeout;
    }

}
