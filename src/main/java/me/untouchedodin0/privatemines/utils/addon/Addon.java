package me.untouchedodin0.privatemines.utils.addon;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Addon {
  String name();
  String author();
  String version();
  String description();
}
