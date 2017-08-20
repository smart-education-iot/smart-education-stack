package com.education.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.common.domain.training.Training;
import com.education.common.domain.training.TrainingSection;
import com.education.common.domain.training.TrainingSectionProcess;
import com.education.common.domain.training.UserTraining;
import com.education.common.utils.Page;
import com.education.manage.mapper.TrainingMapper;
import com.education.manage.service.TrainingService;

@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingMapper trainingMapper;
	@Override
	public List<Training> getTrainingList(int userId, Page<Training> page) {
		return trainingMapper.getTrainingList(userId, page);
	}
	@Override
	public void addTraining(Training training) {
		trainingMapper.addTraining(training);
	}
	@Override
	public List<TrainingSection> getTrainingSectionByTrainingId(int trainingId, int userId, Page<TrainingSection> page) {
		return trainingMapper.getTrainingSectionByTrainingId(trainingId, userId, page);
	}
	@Override
	public List<TrainingSection> getTrainingSectionById(int sectionId, int userId, Page<TrainingSection> page) {
		return trainingMapper.getTrainingSectionById(sectionId, userId, page);
	}
	@Override
	public void deleteTrainingSection(int sectionId, int userId) {
		trainingMapper.deleteTrainingSection(sectionId, userId);
	}
	@Override
	public void addTrainingSection(TrainingSection section) {
		trainingMapper.addTrainingSection(section);
	}
	@Override
	public Map<Integer, List<TrainingSectionProcess>> getTrainingSectionProcessMapByTrainingId(int trainingId, List<Integer> idList,String searchStr) {
		if(idList.size() == 0)
			idList = null;
		List<TrainingSectionProcess> processList = trainingMapper.getTrainingSectionProcessListByTrainingId(trainingId, idList,searchStr, null);
		HashMap<Integer,List<TrainingSectionProcess>> map = new HashMap<Integer,List<TrainingSectionProcess>>();
		for(TrainingSectionProcess process : processList){
			List<TrainingSectionProcess> tmpList = new ArrayList<TrainingSectionProcess>();
			if(map.containsKey(process.getUserId()))
				tmpList = map.get(process.getUserId());
			tmpList.add(process);
			map.put(process.getUserId(), tmpList);
		}
		return map;
	}
	@Override
	public List<UserTraining> getUserTrainingList(int trainingId, int userId,String searchStr, Page<UserTraining> page) {
		return trainingMapper.getUserTrainingList(trainingId, userId,searchStr, page);
	}

}
