LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Define the library name
LOCAL_MODULE := mongoose

# Specify the source files
LOCAL_SRC_FILES := mongoose.c

# Include the necessary headers
LOCAL_C_INCLUDES := $(LOCAL_PATH)

include $(BUILD_SHARED_LIBRARY)

# Define your native library
include $(CLEAR_VARS)

LOCAL_MODULE := my_native_lib

# Specify your native source files
LOCAL_SRC_FILES := my_native_code.c  # Replace with your actual source files

# Link against the mongoose library
LOCAL_STATIC_LIBRARIES := mongoose

include $(BUILD_SHARED_LIBRARY)