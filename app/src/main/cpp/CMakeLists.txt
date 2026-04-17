cmake_minimum_required(VERSION 3.22.1)

project("native-increment-lib")

add_library(
        native-increment-lib
        SHARED
        native-increment-lib.cpp
)

find_library(log-lib log)

target_link_libraries(
        native-increment-lib
        ${log-lib}
)