package me.untouchedodin0.privatemines.utils.addon;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Dependencies {

  Dependency[] value();
//  String name();
//  String version();
//  boolean isAddon();
}
