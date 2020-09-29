package com.smartfit.smartfitapi.model.base;

public class AppEnumBase {
    public enum CourseType {
        WEIGHT_MAINTAIN("Weight Maintain"),
        WEIGHT_LOSS("Weight Loss");
        private final String type;

        CourseType(String type) {
            this.type = type;
        }

        public String getString() {
            return type;
        }
    }

    public enum Level {
        BEGINNER("Beginner"),
        INTERMEDIATE("Intermediate"),
        ADVANCED("Advanced");
        private final String level;

        Level(String level) {
            this.level = level;
        }

        public String getString() {
            return level;
        }
    }

    public enum StepType {
        PRACTICE("Practice"),
        EXERCISE("Exercise"),
        EXERCISE_COUNT("Exercise Count");
        private final String type;

        StepType(String type) {
            this.type = type;
        }

        public String getString() {
            return type;
        }
    }

    public enum OrderType {
        BASIC("Basic"),
        MONTHLY("Monthly");
        private final String type;

        OrderType(String type) {
            this.type = type;
        }

        public String getString() {
            return type;
        }
    }

    public enum PaymentType {
        PAYPAL("Paypal");
        private final String type;

        PaymentType(String type) {
            this.type = type;
        }

        public String getString() {
            return type;
        }
    }

    public enum ActivityStatus {
        DONE("Done"),
        FAIL("Fail"),
        PENDING("Pending");
        private final String status;

        ActivityStatus(String status) {
            this.status = status;
        }

        public String getString() {
            return status;
        }
    }

    public enum Role {
        ADMIN("Admin"),
        STAFF("Staff");
        private final String role;

        Role(String role) {
            this.role = role;
        }

        public String getString() {
            return role;
        }
    }

    public enum MealType {
        CARBS("Carbs"),
        PROTEIN("Protein"),
        FAT("Fat");
        private final String type;

        MealType(String type) {
            this.type = type;
        }

        public String getString() {
            return type;
        }
    }

    public enum UserGoal {
        MAINTAIN_WEIGHT("Maintain Weight"),
        MILE_WEIGHT_LOSS("Mile Weight Loss"),
        WEIGHT_LOSS("Weight Loss"),
        EXTREME_WEIGHT_LOSS("Extreme Weight Loss");
        private final String goal;

        UserGoal(String goal) {
            this.goal = goal;
        }

        public String getString() {
            return goal;
        }
    }
}
