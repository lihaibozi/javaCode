package com.hd.controller.ytdb.timingbackup;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hd.controller.base.BaseController;
import com.hd.service.fhdb.timingbackup.impl.TimingBackUpService;
import com.hd.service.gh.impl.WorkScheduleService;
import com.hd.util.PageData;
import com.hd.util.SmsUtil;

/** quartz 定时任务调度 数据库自动备份工作域
 * @author lihaibo
 * @date 2016-4-10
 */
public class MessageQuartzJob extends BaseController implements Job{

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		Map<String,Object> parameter = (Map<String,Object>)dataMap.get("parameterList");	//获取参数
		
		//普通类从spring容器中拿出service
		WebApplicationContext webctx=ContextLoader.getCurrentWebApplicationContext();
		WorkScheduleService wService = (WorkScheduleService)webctx.getBean("workScheduleService");

		PageData pd = new PageData();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sd = new SimpleDateFormat("yyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			String tomorrow = sd.format(calendar.getTime());
			String today = sd.format(new Date());
			pd.put("today", today+" 00:00:01");
			pd.put("tomorrow", tomorrow+" 23:59:59");
			List<PageData> lists = wService.findFinished(pd);
			IdentityHashMap<String,String> map = new IdentityHashMap<String,String>();
			for(PageData pageData:lists){
				wService.setNotify(pageData);
				String doctorName = pageData.getString("doctorName"); //去到联盟医院的医生
				String cellhone = pageData.getString("cellhone"); //医生电话
				String hosName = pageData.getString("hosName"); //联盟医院
				String contactPhone = pageData.getString("contactPhone"); //联盟医院联系人电话
				String workName = pageData.getString("workName");//工作类别
				String depName = pageData.getString("depName"); //科室
				Object workTime = pageData.get("workTime"); //时间
				map.put(new String(cellhone), URLEncoder.encode("【皖北总院】 "+doctorName+"医生您好:您有一个联盟医院的工作计划,"
						+ "联盟医院:"+hosName+",工作类别:"+workName+",时间:"+sdf.format(workTime),"UTF-8"));
				map.put(new String(contactPhone), URLEncoder.encode("【皖北总院】您好,我院"+doctorName+"医生近期将到您处开展工作,"
						+ "时间:"+sdf.format(workTime)+",工作类别:"+workName+",科室:"+depName,"UTF-8"));
			}
			SmsUtil.sendSms(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**把定时备份任务状态改为关闭
	 * @param pd
	 * @param parameter
	 * @param webctx
	 */
	public void shutdownJob(JobExecutionContext context, PageData pd, Map<String,Object> parameter, WebApplicationContext webctx){
		try {
			context.getScheduler().shutdown();	//备份异常时关闭任务
			TimingBackUpService timingbackupService = (TimingBackUpService)webctx.getBean("timingbackupService");
			pd.put("STATUS", 2);				//改变定时运行状态为2，关闭
			//pd.put("TIMINGBACKUP_ID", parameter.get("TIMINGBACKUP_ID").toString()); //定时备份ID
			timingbackupService.changeStatus(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
