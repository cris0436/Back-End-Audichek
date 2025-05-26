from fastapi import APIRouter, Depends, HTTPException
from schemas.UserSchema import AuthUser, UserCreate, UserOut
from controllers.user.add_user_controller import AddUserController
from controllers.user.imp.add_user_controller_imp import getAddUserControllerImp
from controllers.user.auth_user_controller import AuthUserController
from controllers.user.imp.auth_user_controller_imp import get_auth_controller, get_current_user
from controllers.user.imp.get_user_imp import getGetUserControllerImp
from controllers.user.up_date_person_person import UpDatePerson
from controllers.user.imp.up_date_person_imp import getUpdatePersonControllerImp
from controllers.user.get_user import GetUserController
from controllers.user.imp.remove_person_imp import getRemovePersonControllerImp
from controllers.user.remove_person import RemovePerson

router = APIRouter(
    prefix="/users",
    tags=["Usuarios"]
)

@router.get(
    "/get-session",
    response_model=UserOut,
    response_model_exclude_unset=False,
    summary="Obtener sesión actual",
    description="Retorna la información del usuario actualmente autenticado. **Requiere token de acceso.**"
)
def get_session(current_user: UserOut = Depends(get_current_user)):
    """
    Retorna la información del usuario actualmente autenticado.
    """
    return current_user

@router.post("/", summary="Registrar nuevo usuario")
def create_user(user: UserCreate, add_user_controller: AddUserController = Depends(getAddUserControllerImp)):
    """
    Crea un nuevo usuario y lo registra en el sistema junto con su información personal y rol.
    """
    return add_user_controller.add_user(user)

@router.post("/auth", response_model=dict[str, str], summary="Autenticarse como usuario")
def auth_user(user: AuthUser, user_controller: AuthUserController = Depends(get_auth_controller)):
    """
    Autentica al usuario y retorna un token de acceso.
    """
    return user_controller.auth_user(user)

@router.get("/{user_id}", response_model=UserOut, summary="Obtener usuario por ID")
def read_user(user_id: int,
              get_user_controller: GetUserController = Depends(getGetUserControllerImp),
              current_user: UserOut = Depends(get_current_user)):
    """
    Retorna la información del usuario con el ID especificado.
    """
    return get_user_controller.get_user(user_id)

@router.get("/", response_model=list[UserOut], summary="Listar todos los usuarios")
def read_users(get_user_controller: GetUserController = Depends(getGetUserControllerImp),
               current_user: UserOut = Depends(get_current_user)):
    """
    Retorna una lista de todos los usuarios registrados en el sistema.
    """
    return get_user_controller.get_all_users()

@router.put("/{user_id}", response_model=UserOut, summary="Actualizar usuario")
def update_user(user_id: int, user_update: UserCreate, update_person_controller: UpDatePerson = Depends(getUpdatePersonControllerImp)):
    """
    Actualiza los datos del usuario con el ID proporcionado.
    """
    db_user = update_person_controller.updatePerson(user_id, user_update)
    if db_user is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    return db_user

@router.delete("/{user_id}", summary="Eliminar usuario")
def delete_user(user_id: int, removeperson_controller: RemovePerson = Depends(getRemovePersonControllerImp), current_user: UserOut = Depends(get_current_user)):
    """
    Elimina el usuario especificado por ID.
    """
    user = removeperson_controller.removePerson(user_id)
    if user is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    return {"message": "Usuario eliminado correctamente"}
