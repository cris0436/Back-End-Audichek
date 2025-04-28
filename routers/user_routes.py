from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from database.DatabaseSession import DataBaseSession
from controllers import user_controller
from schemas.UserSchema import AuthUser, UserCreate, UserOut

# Instancia de la sesión de base de datos
db_session = DataBaseSession()

# Crear el router
router = APIRouter(
    prefix="/users",   # Todas las rutas comenzarán con /users
    tags=["Users"]     # Grupo que aparece en Swagger (Documentación)
)

# Crear un usuario
@router.post("/", response_model=UserOut)
def create_user(user: UserCreate, db: Session = Depends(db_session.get_db)):
    return user_controller.create_user(db, user)

# Auth user
@router.post("/auth", response_model=UserOut)
def auth_user(user: AuthUser, db: Session = Depends(db_session.get_db)):
    return user_controller.user_auth(db, user)

# Obtener un usuario por ID
@router.get("/{user_id}", response_model=UserOut)
def read_user(user_id: int, db: Session = Depends(db_session.get_db)):
    db_user = user_controller.get_user(db, user_id)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

# Obtener todos los usuarios
@router.get("/", response_model=list[UserOut])
def read_users(db: Session = Depends(db_session.get_db)):
    return user_controller.get_all_users(db)

# Actualizar un usuario
@router.put("/{user_id}", response_model=UserOut)
def update_user(user_id: int, user_update: dict, db: Session = Depends(db_session.get_db)):
    db_user = user_controller.update_user(db, user_id, user_update)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

# Eliminar un usuario
@router.delete("/{user_id}")
def delete_user(user_id: int, db: Session = Depends(db_session.get_db)):
    db_user = user_controller.delete_user(db, user_id)
    if db_user is None:
        raise HTTPException(status_code=404, detail="User not found")
    return {"message": "User deleted successfully"}