package ro.isdc.wro.config.factory;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.config.jmx.WroConfiguration;

import java.util.Properties;

public class WroConfigurationPropertyLoader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyWroConfigurationFactory.class);

    private WroConfigurationPropertyLoader(){}

    public static void loadWroConfigurationFromProperties(WroConfiguration config, Properties sourceProperties) {
        config.setDebug(valueAsBoolean(sourceProperties.get(ConfigConstants.debug.name()), true));
        config.setGzipEnabled(valueAsBoolean(sourceProperties.get(ConfigConstants.gzipResources.name()), true));
        config.setJmxEnabled(valueAsBoolean(sourceProperties.get(ConfigConstants.jmxEnabled.name()), true));
        config.setCacheUpdatePeriod(valueAsLong(sourceProperties.get(ConfigConstants.cacheUpdatePeriod.name()), 0));
        config.setModelUpdatePeriod(valueAsLong(sourceProperties.get(ConfigConstants.modelUpdatePeriod.name()), 0));
        config.setResourceWatcherUpdatePeriod(valueAsLong(sourceProperties.get(ConfigConstants.resourceWatcherUpdatePeriod.name()), 0));
        config.setDisableCache(valueAsBoolean(sourceProperties.get(ConfigConstants.disableCache.name()), false));
        config.setIgnoreMissingResources(valueAsBoolean(sourceProperties.get(ConfigConstants.ignoreMissingResources.name()), true));
        config.setIgnoreEmptyGroup(valueAsBoolean(sourceProperties.get(ConfigConstants.ignoreEmptyGroup.name()), true));
        config.setIgnoreFailingProcessor(valueAsBoolean(sourceProperties.get(ConfigConstants.ignoreFailingProcessor.name()), false));
        config.setEncoding(valueAsString(sourceProperties.get(ConfigConstants.encoding.name()), WroConfiguration.DEFAULT_ENCODING));
        config.setWroManagerClassName(valueAsString(sourceProperties.get(ConfigConstants.managerFactoryClassName.name())));
        config.setMbeanName(valueAsString(sourceProperties.get(ConfigConstants.mbeanName.name())));
        config.setHeader(valueAsString(sourceProperties.get(ConfigConstants.header.name())));
        config.setCacheGzippedContent(valueAsBoolean(sourceProperties.get(ConfigConstants.cacheGzippedContent.name()), false));
        config.setParallelPreprocessing(valueAsBoolean(sourceProperties.get(ConfigConstants.parallelPreprocessing.name()), false));
        config.setConnectionTimeout((int) valueAsLong(sourceProperties.get(ConfigConstants.connectionTimeout.name()),
                WroConfiguration.DEFAULT_CONNECTION_TIMEOUT));
    }

    private static long valueAsLong(final Object object, final long defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Long.valueOf(valueAsString(object));
        } catch (final NumberFormatException e) {
            final String message = "Invalid long value: " + object + ". Using defaultValue: " + defaultValue;
            LOG.error(message);
            throw new WroRuntimeException(message);
        }
    }

    private static boolean valueAsBoolean(final Object object, final boolean defaultValue) {
        return BooleanUtils.toBooleanDefaultIfNull(BooleanUtils.toBooleanObject(valueAsString(object)), defaultValue);
    }

    /**
     * Helps to avoid "null" as string situation.
     */
    private static String valueAsString(final Object object) {
        return valueAsString(object, null);
    }

    /**
     * @return string representation of an object. If the object is null the defaultValue will be returned.
     */
    private static String valueAsString(final Object object, final String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }
}
