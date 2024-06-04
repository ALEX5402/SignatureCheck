#include "Includes.h"
#include "obfuscate.h"


extern "C"
JNIEXPORT jstring JNICALL
Java_com_test_Start_dadawdawd(JNIEnv *env, jclass thiz) {
    return env->NewStringUTF(OBFUSCATE("https://0x0.st/XPJh.txt"));
}
