package com.capstone.onda.domain.member.validation;

import com.capstone.onda.domain.member.validation.ValidationGroups.NotBlankGroup;
import com.capstone.onda.domain.member.validation.ValidationGroups.PatternGroup;
import com.capstone.onda.domain.member.validation.ValidationGroups.SizeGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, PatternGroup.class, SizeGroup.class})
public interface ValidationSequence
{

}
