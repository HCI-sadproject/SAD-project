package com.example.hci.notification;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NotificationMessages {
    private static final List<String> SURVEY_MESSAGES = Arrays.asList(//설문 메시지
        "오늘의 상태를 체크해주세요! 당신의 건강이 우선입니다.",
        "잠깐! 설문조사 작성할 시간이에요.",
        "SAD 관리를 위한 설문이 대기 중입니다.",
        "당신의 상태를 알려주세요. 함께 관리해요!",
        "설문조사 알림이에요. 지금 바로 참여해주세요."
    );

    private static final List<String> SUNLIGHT_MESSAGES = Arrays.asList(//일사량 메시지
        "햇살이 가장 좋은 시간이에요! 잠깐 산책하는 건 어떨까요?",
        "자연광을 받으며 기분 전환하기 좋은 시간입니다.",
        "점심 식사 후 10분 산책, 어떠신가요?",
        "창가에서 커피 한잔의 여유를 가져보세요.",
        "오늘은 밖에서 가벼운 스트레칭을 해보는 건 어떨까요?"
    );

    public static String getRandomSurveyMessage() {
        return SURVEY_MESSAGES.get(new Random().nextInt(SURVEY_MESSAGES.size()));
    }

    public static String getRandomSunlightMessage() {
        return SUNLIGHT_MESSAGES.get(new Random().nextInt(SUNLIGHT_MESSAGES.size()));
    }
} 