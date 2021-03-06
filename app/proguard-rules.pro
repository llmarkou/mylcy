# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses

# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后可以使用printmapping指定映射文件的名称
-verbose

# 不启用优化,建议使用此选项
# proguard的优化选项和java虚拟机中的字节码dex优化有冲突，可能会产生一些未知的问题
-dontoptimize

# 不做预校验，preverify是proguard的4个步骤之一
# Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify

# 保留注解，因为注解是通过反射机制来实现的
-keepattributes *Annotation*

# 保留本地NDK方法
# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留View中的set和get方法
# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# 保留Activity当中的View相关方法
# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# 保留枚举类
# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留序列号相关类即方法
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

# 保留资源应用名
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 忽略support包下的警告
-dontwarn android.support.**

# 保留support包下的动画
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

#retrofit2 start##
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keep class okhttp3.** { *; }
-dontwarn okio.**
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
#retrofit2 end##
