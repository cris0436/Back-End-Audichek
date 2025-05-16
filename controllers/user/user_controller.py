from sqlalchemy.orm import Session
from models.entities import Person
from models.entities import User
from models.entities import Admin
from schemas.UserSchema import AuthUser, UserCreate
from sqlalchemy.exc import IntegrityError
from exceptions.db_exceptions import handle_integrity_error  # <-- asegúrate de que esto exista
from fastapi import HTTPException

from models.entities import Rol  # <- Importamos el modelo Rol


def user_auth(db: Session, user_data: AuthUser):
    # Verificar si el usuario existe
    user = db.query(User).filter(User.username == user_data.username).first()
    if not user or user.password != user_data.password:
        # Lanzar un error específico cuando las credenciales son incorrectas
        raise HTTPException(status_code=400, detail="Credenciales incorrectas")

    try:
        # Si el usuario existe y la contraseña coincide, retornar el usuario
        return user
    except IntegrityError as e:
        print(f"Error de integridad: {e}")
        raise HTTPException(status_code=400, detail=f"Error de integridad: {e}")
    except Exception as e:
        print(f"Error inesperado al autenticar el usuario: {e}")
        raise HTTPException(status_code=500, detail="Error inesperado al autenticar el usuario")


# Actualizar un usuario

def update_user(db: Session, user_id: int, updated_data: dict):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        return None
    for key, value in updated_data.items():
        if hasattr(user, key):
            setattr(user, key, value)
    db.commit()
    db.refresh(user)
    return user

# Eliminar un usuario
def delete_user(db: Session, user_id: int):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        return None
    db.delete(user)
    db.commit()
    return user