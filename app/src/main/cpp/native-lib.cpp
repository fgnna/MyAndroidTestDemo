#include <jni.h>
#include <string>
#include <android/log.h>
extern "C" JNIEXPORT jstring JNICALL
Java_someday_com_ndk_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C+++++++";
    __android_log_print(ANDROID_LOG_INFO, "JNITag","string From Java To C :");
    return env->NewStringUTF(hello.c_str());
}
