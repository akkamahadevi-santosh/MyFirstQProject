package com.qutap.dash.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qutap.dash.commonUtils.DateUtility;
import com.qutap.dash.commonUtils.Response;
import com.qutap.dash.customException.ModuleException;
import com.qutap.dash.customException.ProjectInfoException;
import com.qutap.dash.domain.ModuleDomain;
import com.qutap.dash.domain.ProjectInfoDomain;
import com.qutap.dash.model.ModuleModel;
import com.qutap.dash.model.ProjectInfoModel;
import com.qutap.dash.repository.ModuleDao;

@Service
public class ModuleServiceImpl implements ModuleService {

	Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

	@Autowired
	ModuleDao moduleDao;

	@Override
	public Response saveModuleModel(List<ModuleModel> moduleModellist, String projectId) {
		try {
			List<ModuleDomain> moduleDomainList = new ArrayList<>();
			moduleModellist.stream().forEach(tmp -> {
				tmp.setModuleId(new ObjectId().toString());
			tmp.setCreatedDate(DateUtility.getDate(new Date(), DateUtility.FORMAT_1));
				ModuleDomain moduleDomain = new ModuleDomain();
				BeanUtils.copyProperties(tmp, moduleDomain);
				moduleDomainList.add(moduleDomain);
			});
			Response response = moduleDao.saveModule(moduleDomainList, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in saving module details", e);
			throw new ModuleException("error in saving module details");
		}
	}
	
	@Override
	public ModuleModel getModuleById(String moduleId, String projectId) {
		try {
			ModuleDomain moduleDomain = moduleDao.getModuleById(moduleId, projectId);
			ModuleModel moduleModel = new ModuleModel();
			BeanUtils.copyProperties(moduleDomain, moduleModel);
			return moduleModel;
		} catch (Exception e) {
			log.error("error in getting module details", e);
			throw new ModuleException("error in getting module details");
		}
	}


	@Override
	public ModuleModel getModuleByName(String moduleName, String projectName) {
		try {
		ModuleDomain moduleDomain = moduleDao.getModuleByName(moduleName, projectName);
		ModuleModel moduleModel = new ModuleModel();
		BeanUtils.copyProperties(moduleDomain, moduleModel);
			return moduleModel;
		} catch (Exception e) {
			log.error("error in getting module details", e);
			throw new ModuleException("error in getting module details");
		}
	}
	
	@Override
	public List<ModuleModel> getModuleList(String projectId) {
		try {
			List<ModuleModel> moduleModelList = new ArrayList<ModuleModel>();
			List<ModuleDomain> moduleDomainList = moduleDao.getModuleList(projectId);
			for (ModuleDomain moduleDomain : moduleDomainList) {
				ModuleModel moduleModel = new ModuleModel();
				BeanUtils.copyProperties(moduleDomain, moduleModel);
				moduleModelList.add(moduleModel);
			}
			return moduleModelList;
		} catch (Exception e) {
			log.error("error in getting list of modules details", e);
			throw new ModuleException("error in getting list of modules details");
		}
	}

	@Override
	public Response updateModule(ModuleModel moduleModel, String projectId) {
		try {
			ModuleDomain moduleDomain = new ModuleDomain();
			BeanUtils.copyProperties(moduleModel, moduleDomain);
			Response response = moduleDao.updateModule(moduleDomain, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in updating module details", e);
			throw new ModuleException("error in updating module details");
		}
	}

	@Override
	public Response deleteModuleModel(String moduleId, String projectId) {
		try {
			Response response = moduleDao.deleteModule(moduleId, projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting module details", e);
			throw new ModuleException("error in deleting module details");
		}
	}

	@Override
	public Response deleteAllModuleModel(String projectId) {
		try {
			Response response = moduleDao.deleteAllModule( projectId);
			return response;
		} catch (Exception e) {
			log.error("error in deleting all module details", e);
			throw new ModuleException("error in deleting all module details");
		}
	}


	
	

}