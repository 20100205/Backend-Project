package BackendProject.entities.enums;

public enum Permission {
    USER_EDIT("user:edit"), USER_READ("user:read"),
    PRODUCT_ADD("product:add"), PRODUCT_EDIT("product:edit"), PRODUCT_READ("product:read"),
    NONE("none");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
