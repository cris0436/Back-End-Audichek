from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from models.persistence.DatabaseSession import DataBaseSession
from controllers.user import user_controller
from schemas.UserSchema import AuthUser, UserCreate, UserOut
from controllers.user.add_user_controller import AddUserController
from controllers.user.imp.add_user_controller_imp import getAddUserControllerImp
from controllers.user.auth_user_controller import AuthUserController
from controllers.user.imp.auth_user_controller_imp import AuthUserControllerImp ,get_auth_controller,get_current_user
from controllers.user.imp.get_user_imp import GetUserImp ,getGetUserControllerImp
from controllers.user.up_date_person_person import UpDatePerson
from controllers.user.imp.up_date_person_imp import UpdatePersonImp ,getUpdatePersonControllerImp
from controllers.user.get_user import GetUserController
from controllers.user.imp.remove_person_imp import RemovePersonImp ,getRemovePersonControllerImp
from controllers.user.remove_person import RemovePerson

# Definir el router
router = APIRouter(prefix="/users", tags=["Users"])


@router.post("/", response_model=UserOut)
def create_user(user: UserCreate, add_user_controller: AddUserController = Depends(getAddUserControllerImp)):
    return add_user_controller.add_user(user)

# Autenticación de usuario
@router.post("/auth")
def auth_user(user: AuthUser, user_controller : AuthUserController = Depends(get_auth_controller)):
    return user_controller.auth_user(user)

@router.get("/{user_id}", response_model=UserOut)
def read_user(user_id: int,
              get_user_controller: GetUserController = Depends(getGetUserControllerImp),
              current_user: UserOut = Depends(get_current_user)):
    return get_user_controller.get_user(user_id)

# Obtener todos los usuarios
@router.get("/", response_model=list[UserOut])
def read_users(get_user_controller: GetUserController = Depends(getGetUserControllerImp),
               current_user:UserOut = Depends(get_current_user)):
    return get_user_controller.get_all_users()

@router.put("/{user_id}", response_model=UserOut)
def update_user(user_id: int, user_update:UserCreate , update_person_controller: UpDatePerson = Depends(getUpdatePersonControllerImp)):
    db_user = update_person_controller.updatePerson(user_id, user_update)  # Convert user_update to dict if necessary
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

# Eliminar un usuario
@router.delete("/{user_id}")
def delete_user(user_id: int, removeperson_controller: RemovePerson = Depends(getRemovePersonControllerImp), current_user: UserOut = Depends(get_current_user)):
    user=removeperson_controller.removePerson(user_id)
    if user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return {"message": "User deleted successfully"}
