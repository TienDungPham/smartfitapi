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
                    "Cool Down Stretch",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404977/3_wiluqj.jpg",
                    "A quick and simple beginner flexibility routine that can be done in 9 minutes",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.BEGINNER.getString(),
                    2,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createCourseWithStep(
                    "Deskercise",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404977/4_lfcspo.jpg",
                    "Try this office stretching workout next time you are feeling tired at work to boot your energy levels and productivity",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.BEGINNER.getString(),
                    2,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createCourseWithStep(
                    "Goodnight Stretch",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404977/6_ucbq4k.jpg",
                    "At the end of a stressful day, try these stretches on your bed to help relax and fall asleep quickly, and better sounder sleep is awaiting!",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.INTERMEDIATE.getString(),
                    1,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createCourseWithStep(
                    "Knee Friendly",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404977/8_fvcluo.jpg",
                    "Got some knee pain but still want to work up a sweat? Give this knee friendly a try",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.INTERMEDIATE.getString(),
                    1,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createCourseWithStep(
                    "Morning Espresso",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404976/5_tjh6fv.jpg",
                    "Plan to be a morning person? Get moving and start an energizing morning along with this workout",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.ADVANCED.getString(),
                    1,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createCourseWithStep(
                    "Total Stretch",
                    "https://res.cloudinary.com/dtasyh91f/image/upload/v1601404976/10_ugxt4h.jpg",
                    "This workout will increases flexibility and range of motion and allows you to get the most out of your workous",
                    AppEnumBase.CourseType.WEIGHT_LOSS.getString(),
                    AppEnumBase.Level.ADVANCED.getString(),
                    1,
                    AppEnumBase.OrderType.MONTHLY.getString()
            );

            createMeal("Bread", "", AppEnumBase.MealType.CARBS.getString(), 75);
            createMeal("Butter", "", AppEnumBase.MealType.CARBS.getString(), 102);
            createMeal("Cheeseburger", "", AppEnumBase.MealType.CARBS.getString(), 285);
            createMeal("Pizza", "", AppEnumBase.MealType.CARBS.getString(), 285);

            createMeal("Beef", "", AppEnumBase.MealType.PROTEIN.getString(), 142);
            createMeal("Chicken", "", AppEnumBase.MealType.PROTEIN.getString(), 136);
            createMeal("Tofu", "", AppEnumBase.MealType.PROTEIN.getString(), 86);
            createMeal("Egg", "", AppEnumBase.MealType.PROTEIN.getString(), 78);

            createMeal("Hamburger", "", AppEnumBase.MealType.FAT.getString(), 250);
            createMeal("Beer", "", AppEnumBase.MealType.FAT.getString(), 154);
            createMeal("Apple cider", "", AppEnumBase.MealType.FAT.getString(), 117);
            createMeal("Yogurt ", "", AppEnumBase.MealType.FAT.getString(), 110);
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
                .name("Tree")
                .type(AppEnumBase.StepType.PRACTICE.getString())
                .description("<ol><li>Take a moment to feel both your feet root into the floor with your weight distributed equally on all four corners of each foot.</li><li>Begin to shift your weight into your right foot, lifting your left foot off the floor. Keep your right leg straight but don't lock the knee.</li><li>Bend your left knee and bring the sole of your left foot high onto your inner right thigh. </li><li>Press your foot into your thigh and your thigh back into your foot with equal pressure. This will help you keep both hips squared toward the front so your right hip doesn't jut out.</li><li>Focus your gaze on something that doesn't move to help you keep your balance.</li><li>Take 5 to 10 breaths, then lower your left foot to the floor and do the other side.</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601408194/videos/how-to-do-a-butt-kick_ruerlt.mp4")
                .pose("")
                .course(course)
                .build());
        courseSteps.add(CourseStep.builder()
                .name("Tree")
                .type(AppEnumBase.StepType.EXERCISE.getString())
                .description("<ol><li>Take a moment to feel both your feet root into the floor with your weight distributed equally on all four corners of each foot.</li><li>Begin to shift your weight into your right foot, lifting your left foot off the floor. Keep your right leg straight but don't lock the knee.</li><li>Bend your left knee and bring the sole of your left foot high onto your inner right thigh. </li><li>Press your foot into your thigh and your thigh back into your foot with equal pressure. This will help you keep both hips squared toward the front so your right hip doesn't jut out.</li><li>Focus your gaze on something that doesn't move to help you keep your balance.</li><li>Take 5 to 10 breaths, then lower your left foot to the floor and do the other side.</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/image/upload/v1602133184/ezgif.com-gif-maker_wr9ecu.gif")
                .pose("tree")
                .course(course)
                .build());

        courseSteps.add(CourseStep.builder()
                .name("Triangle")
                .type(AppEnumBase.StepType.PRACTICE.getString())
                .description("<ol><li>Engage your right thigh muscles and draw your right femur into its socket. Extend your right hand toward the front of the room, keeping your right hip tucked.</li><li>Lower your right hand down onto your shin or ankle. If you are more open, bring your right hand to the floor on the inside or on the right foot. Do whichever one feels most comfortable.</li><li>The left shoulder stacks on top of the right one as you open your chest, reaching your left fingertips toward the ceiling while keeping your left shoulder rooted in its socket. </li><li>Turn your head to take your gaze up toward your left fingertips. If this is uncomfortable for your neck, it's also fine to keep the head in a more neutral position. </li><li>Continue to draw your right thigh muscles upward, deepening the crease in your right hip.</li><li>Soften your right knee slightly to prevent hyperextension (this is called a microbend).</li><li>Stay for at least 5 breaths.</li><li>Repeat the pose with your left leg forward.</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601408194/videos/how-to-do-a-butt-kick_ruerlt.mp4")
                .pose("")
                .course(course)
                .build());
        courseSteps.add(CourseStep.builder()
                .name("Triangle")
                .type(AppEnumBase.StepType.EXERCISE.getString())
                .description("<ol><li>Engage your right thigh muscles and draw your right femur into its socket. Extend your right hand toward the front of the room, keeping your right hip tucked.</li><li>Lower your right hand down onto your shin or ankle. If you are more open, bring your right hand to the floor on the inside or on the right foot. Do whichever one feels most comfortable.</li><li>The left shoulder stacks on top of the right one as you open your chest, reaching your left fingertips toward the ceiling while keeping your left shoulder rooted in its socket. </li><li>Turn your head to take your gaze up toward your left fingertips. If this is uncomfortable for your neck, it's also fine to keep the head in a more neutral position. </li><li>Continue to draw your right thigh muscles upward, deepening the crease in your right hip.</li><li>Soften your right knee slightly to prevent hyperextension (this is called a microbend).</li><li>Stay for at least 5 breaths.</li><li>Repeat the pose with your left leg forward.</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/image/upload/v1602133184/ezgif.com-gif-maker_wr9ecu.gif")
                .pose("triangle")
                .course(course)
                .build());

        courseSteps.add(CourseStep.builder()
                .name("Reverse Warrior")
                .type(AppEnumBase.StepType.PRACTICE.getString())
                .description("<ol><li>Lean your torso toward the front of your mat and then circle your right hand up toward the ceiling for a big stretch along your right side. Keep your right arm plugged into the shoulder socket. Your left hand comes down to rest lightly on the back of your left thigh.</li><li>Bring your gaze up to the right fingertips.</li><li>Hold for five breaths and then switch sides.</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/video/upload/v1601408194/videos/how-to-do-a-butt-kick_ruerlt.mp4")
                .pose("")
                .course(course)
                .build());
        courseSteps.add(CourseStep.builder()
                .name("Reverse Warrior")
                .type(AppEnumBase.StepType.EXERCISE.getString())
                .description("<ol><li>Lean your torso toward the front of your mat and then circle your right hand up toward the ceiling for a big stretch along your right side. Keep your right arm plugged into the shoulder socket. Your left hand comes down to rest lightly on the back of your left thigh.</li><li>Bring your gaze up to the right fingertips.</li><li>Hold for five breaths and then switch sides.</li></ol>")
                .videoUrl("https://res.cloudinary.com/dtasyh91f/image/upload/v1602133184/ezgif.com-gif-maker_wr9ecu.gif")
                .pose("reversewarrior")
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
