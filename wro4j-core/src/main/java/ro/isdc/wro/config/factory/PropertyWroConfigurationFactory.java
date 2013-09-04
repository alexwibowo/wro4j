/*
 * Copyright (C) 2011. All rights reserved.
 */
package ro.isdc.wro.config.factory;

import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.config.jmx.WroConfiguration;
import ro.isdc.wro.util.ObjectFactory;


/**
 * Loads configurations from a {@link Properties} object.
 * 
 * @author Alex Objelean
 * @created 10 May 2011
 * @since 1.3.7
 */
public class PropertyWroConfigurationFactory
    implements ObjectFactory<WroConfiguration> {
  private static final Logger LOG = LoggerFactory.getLogger(PropertyWroConfigurationFactory.class);
  /**
   * Holds configuration options. If no properties are set, the default values will be used instead.
   */
  private final Properties properties;
  
  public PropertyWroConfigurationFactory() {
    this(new Properties());
  }
  
  public PropertyWroConfigurationFactory(final Properties props) {
    Validate.notNull(props);
    this.properties = props;
  }
  

  /**
   * {@inheritDoc}
   */
  public WroConfiguration create() {
    final WroConfiguration config = new WroConfiguration();
    WroConfigurationPropertyLoader.loadWroConfigurationFromProperties(config, properties);
    LOG.debug("WroConfiguration created: {}", config);
    return config;
  }
}
