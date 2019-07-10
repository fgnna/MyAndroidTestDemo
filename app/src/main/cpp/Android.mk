
LOCAL_PATH := $(call my-dir)

#include $(CLEAR_VARS)
#LOCAL_MODULE := base
#LOCAL_SRC_FILES := libbase.a
#LOCAL_EXPORT_C_INCLUDES := base
#include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := eagleeyenative
LOCAL_SRC_FILES := entry.c find_file_path/fd2path.c hooks/hook_apis.c hooks/util.c base/hook.c base/util.c
LOCAL_LDLIBS:= -L$(SYSROOT)/usr/lib -llog
LOCAL_CFLAGS := -g
LOCAL_SHARED_LIBRARIES := dl
#LOCAL_STATIC_LIBRARIES := base
include $(BUILD_SHARED_LIBRARY)
