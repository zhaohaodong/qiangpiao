// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.1
//
// <auto-generated>
//
// Generated from file `EnterpriseWeChatI.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.example.qiangpiao.wechat;

public enum EnterpriseWeChatMessageType implements java.io.Serializable
{
    
    TEXT(1),
    
    IMAGE(2),
    
    VOICE(3),
    
    VIDEO(4),
    
    FILE(5),
    
    NEWS(6),
    
    MPNEWS(7);

    public int
    value()
    {
        return __value;
    }

    public static EnterpriseWeChatMessageType
    valueOf(int __v)
    {
        switch(__v)
        {
        case 1:
            return TEXT;
        case 2:
            return IMAGE;
        case 3:
            return VOICE;
        case 4:
            return VIDEO;
        case 5:
            return FILE;
        case 6:
            return NEWS;
        case 7:
            return MPNEWS;
        }
        return null;
    }

    private
    EnterpriseWeChatMessageType(int __v)
    {
        __value = __v;
    }
    private final int __value;
}
