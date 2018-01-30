package BE.responsemodels;

import java.util.List;

public class UserModel {

    class ProjectListModel {
        private String project_name;
        private String access_level;

        public ProjectListModel(String project_name, String access_level) {
            this.project_name = project_name;
            this.access_level = access_level;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getAccess_level() {
            return access_level;
        }

        public void setAccess_level(String access_level) {
            this.access_level = access_level;
        }
    }

    private String username;

    private String password;

    private String email;

    private List<ProjectListModel> projects;

}
