package com.smartfit.smartfitapi.service;

import com.smartfit.smartfitapi.entity.UserPayment;
import com.smartfit.smartfitapi.entity.UserProfile;
import com.smartfit.smartfitapi.model.transfer.AdminAnalyticDTO;
import com.smartfit.smartfitapi.model.transfer.AnalyticDTO;
import com.smartfit.smartfitapi.model.transfer.CountDTO;
import com.smartfit.smartfitapi.model.transfer.DatasetDTO;
import com.smartfit.smartfitapi.repository.*;
import com.smartfit.smartfitapi.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AdminAnalyticService {
    @Autowired
    private UserPaymentRepository userPaymentRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StaffProfileRepository staffProfileRepository;

    public ServiceResult<AdminAnalyticDTO> analytic() {
        ServiceResult<AdminAnalyticDTO> result = new ServiceResult<>();
        try {
            AnalyticDTO userPaymentAnalytic = userPaymentAnalytic();
            AnalyticDTO userTrendAnalytic = userTrendAnalytic();

            AdminAnalyticDTO resultData = new AdminAnalyticDTO();
            resultData.setMonthlyIncome(userPaymentAnalytic);
            resultData.setUserWorkoutTrend(userTrendAnalytic);

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.FAIL);
        }
        return result;
    }

    public ServiceResult<CountDTO> countInfo() {
        ServiceResult<CountDTO> result = new ServiceResult<>();
        try {
            CountDTO resultData = new CountDTO();
            resultData.setTotalUser(userProfileRepository.findAll().size());
            resultData.setTotalActiveUser(
                    (int) userSessionRepository.findAll()
                            .stream()
                            .filter(us -> us.getAccessTime().compareTo(us.getLeaveTime()) == 0)
                            .count()
            );
            resultData.setTotalCourse(courseRepository.findAll().size());
            resultData.setTotalStaff(staffProfileRepository.findAll().size());

            result.setData(resultData);
            result.setCode(ServiceResult.ResultCode.SUCCESS);
        } catch (Exception e) {
            result.setCode(ServiceResult.ResultCode.FAIL);
        }
        return result;
    }

    private AnalyticDTO userPaymentAnalytic() {
        AnalyticDTO userPaymentAnalytic = new AnalyticDTO();
        try {
            List<UserPayment> userPayments = userPaymentRepository.findAll();
            List<UserPayment> userPaymentInThisYear = userPayments
                    .stream()
                    .filter(up -> up.getCreatedAt().getYear() == new Date().getYear())
                    .collect(Collectors.toList());

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Map<String, Integer> userPaymentGroupByMonthAndYear = new LinkedHashMap<>();
            for (String month : new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}) {
                String compareDateString = "" + month + "/01/" + Calendar.getInstance().get(Calendar.YEAR) + "";
                Date compareDate = df.parse(compareDateString);
                AtomicReference<Integer> totalIncome = new AtomicReference<>(0);
                userPaymentInThisYear.forEach(up -> {
                    if (isSameMonthAndYear(up.getCreatedAt(), compareDate)) {
                        totalIncome.updateAndGet(v -> v + up.getTotalPrice());
                    }
                });
                userPaymentGroupByMonthAndYear.put(compareDateString, totalIncome.get());
            }

            List<String> labels = new LinkedList<>();
            List<DatasetDTO> datasets = new LinkedList<>();

            DatasetDTO dataset = new DatasetDTO();
            List<Integer> data = new LinkedList<>();
            userPaymentGroupByMonthAndYear.forEach((k, v) -> {
                labels.add(k);
                data.add(v);
            });
            dataset.setLabel("Monthly Income");
            dataset.setData(data);
            datasets.add(dataset);

            userPaymentAnalytic.setLabels(labels);
            userPaymentAnalytic.setDatasets(datasets);
            return userPaymentAnalytic;
        } catch (Exception e) {
            return userPaymentAnalytic;
        }
    }


    private AnalyticDTO userTrendAnalytic() {
        AnalyticDTO userTrendAnalytic = new AnalyticDTO();
        try {
            List<UserProfile> userProfiles = userProfileRepository.findAll();
            Map<String, List<UserProfile>> userTrends = userProfiles.stream().collect(
                    Collectors.groupingBy(up -> up.getGoal())
            );

            List<String> labels = new LinkedList<>();
            List<DatasetDTO> datasets = new LinkedList<>();
            DatasetDTO dataset = new DatasetDTO();
            List<Integer> data = new LinkedList<>();

            userTrends.forEach((k, v) -> {
                labels.add(k);
                data.add(v.size());
            });

            dataset.setLabel("User Trend");
            dataset.setData(data);
            datasets.add(dataset);

            userTrendAnalytic.setLabels(labels);
            userTrendAnalytic.setDatasets(datasets);
            return userTrendAnalytic;
        } catch (Exception e) {
            return userTrendAnalytic;
        }
    }

    private boolean isSameMonthAndYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}
