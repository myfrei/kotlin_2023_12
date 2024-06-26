package com.fitness.helper.exception

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Unknown request class: $clazz")