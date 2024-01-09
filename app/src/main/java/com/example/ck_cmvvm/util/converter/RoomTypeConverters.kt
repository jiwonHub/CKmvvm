package com.example.ck_cmvvm.util.converter

import androidx.room.TypeConverter

object RoomTypeConverters {

    @TypeConverter
    // Room 데이터베이스에 저장되는 사용자 정의 타입을 관리하기 위해 사용
    // Pair<Int, Int> 타입은 Room데이터베이스에 직접 지원이 되지 않기때문에 RoomTypeConverters을 사용하여 처리
    @JvmStatic
    fun toString(pair: Pair<Int, Int>): String { // Pair<Int, Int> 타입을 String 타입으로 변환하는 메서드
        return "${pair.first},${pair.second}" // pair의 첫 번째와 두 번째 요소를 쉼표로 구분하여 하나의 문자열로 결합.
    }

    @TypeConverter
    @JvmStatic
    fun toIntPair(str: String): Pair<Int, Int> { // 문자열을 Pair<Int, Int>타입으로 변환하는 메서드
        val splitedStr = str.split(",") // 입력된 문자열을 쉼표로 분리
        return Pair(Integer.parseInt(splitedStr[0]), Integer.parseInt(splitedStr[1])) // 분리된 두 부분을 분리하여 두 부분을 각각 정수로 변환 후 Pair로 조합
    }

}