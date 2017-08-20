package com.education.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.common.domain.training.Training;
import com.education.common.domain.training.TrainingSection;
import com.education.common.domain.training.TrainingSectionProcess;
import com.education.common.domain.training.UserTrainingHistory;
import com.education.common.utils.Page;
import com.education.portal.mapper.TrainingMapper;
import com.education.portal.service.TrainingService;

@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingMapper trainingMapper;
	@Override
	public List<Training> getTrainingList(Page<Training> page) {
		return trainingMapper.getTrainingList(page);
	}
	
	@Override
	public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, Page<TrainingSection> page) {
		return trainingMapper.getTrainingSectionByTrainingId(trainingId, page);
	}
	@Override
	public List<TrainingSection> getTrainingSectionById(int sectionId, Page<TrainingSection> page) {
		return trainingMapper.getTrainingSectionById(sectionId, page);
	}

	@Override
	public UserTrainingHistory getTrainingHistBySectionId(int sectionId, int userId) {
		return trainingMapper.getTrainingHistBySectionId(sectionId, userId);
	}

	@Override
	public void setUserTrainingHistory(UserTrainingHistory hist) {
		trainingMapper.setUserTrainingHistory(hist);
	}

	@Override
	public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByUserId(int userId) {
		// TODO Auto-generated method stub
		List<TrainingSectionProcess> processList = trainingMapper.getTrainingSectionProcessListByUserId(userId, null);
		HashMap<Integer,List<TrainingSectionProcess>> map = new HashMap<Integer,List<TrainingSectionProcess>>();
		for(TrainingSectionProcess process : processList){
			List<TrainingSectionProcess> tmpList = new ArrayList<TrainingSectionProcess>();
			if(map.containsKey(process.getTrainingId()))
				tmpList = map.get(process.getTrainingId());
			tmpList.add(process);
			map.put(process.getTrainingId(), tmpList);
		}
		return map;
	}
}
