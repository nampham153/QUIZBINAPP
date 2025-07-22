package com.example.quizbin1.utils;

import com.example.quizbin1.data.model.dto.OptionDTO;
import com.example.quizbin1.data.model.dto.QuestionDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GlobalDataHolder {
    public static List<QuestionDTO> questionList;
    public static Map<UUID, List<OptionDTO>> optionMap = new HashMap<>();
    public static List<UUID> selectedOptionIds;
}
