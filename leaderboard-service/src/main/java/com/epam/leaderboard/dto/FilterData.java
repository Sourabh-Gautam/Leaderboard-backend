package com.epam.leaderboard.dto;

import java.util.List;

import com.epam.leaderboard.entity.Participant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilterData {
	private List<Participant> participants;
	private int totalElements;
}
