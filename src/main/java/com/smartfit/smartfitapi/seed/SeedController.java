package com.smartfit.smartfitapi.seed;

import com.smartfit.smartfitapi.entity.*;
import com.smartfit.smartfitapi.model.base.AppEnumBase;
import com.smartfit.smartfitapi.repository.*;
import com.smartfit.smartfitapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/seed")
public class SeedController {
    @Autowired
    private StaffAccountRepository staffAccountRepository;
    @Autowired
    private StaffProfileRepository staffProfileRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseStepRepository courseStepRepository;
    @Autowired
    private MealRepository mealRepository;

    @GetMapping(path = "/seed")
    public ResponseEntity<Boolean> seed() {
        try {
            StaffProfile staffProfile = createStaffProfile();
            StaffAccount staffAccount = createStaffAccount(staffProfile);

            createCourseWithStep(
                    "Chest Toner",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404977/3_wiluqj.jpg",
                    "Easy exercises, but effective. Follow this workout to tone and define your chest",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.BEGINNER.getString(),
                    2,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createMeal("Apple", "", AppEnumBase.MealType.CARBS.getString(), 59);
            createMeal("Banana", "", AppEnumBase.MealType.CARBS.getString(), 151);
            createMeal("Grapes", "", AppEnumBase.MealType.CARBS.getString(), 100);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private StaffProfile createStaffProfile() {
        StaffProfile staffProfile = StaffProfile.builder()
                .name("Jack London")
                .imageUrl("https://res.cloudinary.com/dtasyh91f/image/upload/v1601405117/public/public_1_gxulfz.png")
                .address("201 46th St, Brooklyn, NY 11220, USA")
                .role(AppEnumBase.Role.ADMIN.getString())
                .build();
        return staffProfileRepository.save(staffProfile);
    }

    private StaffAccount createStaffAccount(StaffProfile staffProfile) {
        StaffAccount staffAccount = StaffAccount.builder()
                .staffProfile(staffProfile)
                .email("jack@gmail.com")
                .password(Utils.hashPassword("jack"))
                .isVerified(true)
                .build();
        return staffAccountRepository.save(staffAccount);
    }

    private void createCourseWithStep(
            String name,
            String imageUrl,
            String description,
            String courseType,
            String level,
            Integer estimatedTime,
            String orderType) {
        Course course = Course.builder()
                .name(name)
                .imageUrl(imageUrl)
                .description(description)
                .courseType(courseType)
                .level(level)
                .estimatedTime(estimatedTime)
                .orderType(orderType)
                .build();
        course = courseRepository.save(course);
        List<CourseStep> courseSteps = new LinkedList<>();
        courseSteps.add(CourseStep.builder()
                .name("Butt Kicks")
                .type(AppEnumBase.StepType.PRACTICE.getString())
                .description("<ol><li>Start standing tall and bring one heel off the floor towards your glutes, the opposite hand comes up towards your shoulder like running arms</li><li>Switch to the other side</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601408194/videos/how-to-do-a-butt-kick_ruerlt.mp4")
                .pose("")
                .course(course)
                .build());
        courseSteps.add(CourseStep.builder()
                .name("Butt Kicks")
                .type(AppEnumBase.StepType.EXERCISE_COUNT.getString())
                .description("<ol><li>Start standing tall and bring one heel off the floor towards your glutes, the opposite hand comes up towards your shoulder like running arms</li><li>Switch to the other side</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601408194/videos/how-to-do-a-butt-kick_ruerlt.mp4")
                .pose("butt_kicks")
                .course(course)
                .build());

        courseSteps.add(CourseStep.builder()
                .name("Squat Jumps")
                .type(AppEnumBase.StepType.PRACTICE.getString())
                .description("<ol><li>Stand with your feet shoulder-width apart</li><li>Start by doing a regular squat, then engage your core and jump up explosively</li><li>When you land, lower your body back into the squat position</li><li>Repeat</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601407864/videos/how-to-do-a-squat-jump_yo9bsa.mp4")
                .pose("")
                .course(course)
                .build());
        courseSteps.add(CourseStep.builder()
                .name("Squat Jumps")
                .type(AppEnumBase.StepType.EXERCISE.getString())
                .description("<ol><li>Stand with your feet shoulder-width apart</li><li>Start by doing a regular squat, then engage your core and jump up explosively</li><li>When you land, lower your body back into the squat position</li><li>Repeat</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601407864/videos/how-to-do-a-squat-jump_yo9bsa.mp4")
                .pose("squat_jumps")
                .course(course)
                .build());
        courseStepRepository.saveAll(courseSteps);
    }

    public void createMeal(
            String name,
            String imageUrl,
            String type,
            Integer calories
    ) {
        Meal meal = Meal.builder()
                .name(name)
                .imageUrl(imageUrl)
                .type(type)
                .calories(calories)
                .build();
        mealRepository.save(meal);
    }
}
