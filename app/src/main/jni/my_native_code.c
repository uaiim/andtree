#include <jni.h>
#include "mongoose.h"

static struct mg_mgr mgr;

void start_server() {
    mg_mgr_init(&mgr, NULL);
    mg_http_listen(&mgr, "http://0.0.0.0:8000", ev_handler, NULL);
}

void ev_handler(struct mg_connection *c, int ev, void *ev_data) {
    // Handle events here
}

JNIEXPORT void JNICALL
Java_com_example_jdk17_MainActivity_startServer(JNIEnv *env, jobject obj) {
    start_server();
}

JNIEXPORT void JNICALL
Java_com_example_jdk17_MainActivity_stopServer(JNIEnv *env, jobject obj) {
    mg_mgr_free(&mgr);
}