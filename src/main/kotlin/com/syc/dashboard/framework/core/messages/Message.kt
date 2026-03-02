package com.syc.dashboard.framework.core.messages

abstract class Message(
    var id: String = "",
    var remoteAddress: String = "",
    var remoteHostName: String = "",
    var triggeredBy: String = "",
)
