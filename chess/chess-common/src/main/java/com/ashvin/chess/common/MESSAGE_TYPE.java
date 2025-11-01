package com.ashvin.chess.common;
import com.google.gson.annotations.*;
public enum MESSAGE_TYPE
{
@SerializedName("CHALLENGE") CHALLENGE,
@SerializedName("CHALLENGE_ACCEPTED") CHALLENGE_ACCEPTED,
@SerializedName("CHALLENGE_REJECTED") CHALLENGE_REJECTED,
@SerializedName("NOT_AVAILABLE") NOT_AVAILABLE
}
