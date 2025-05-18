from sqlalchemy.orm import Session
from models.entities import Person
from models.entities import User
from models.entities import Admin
from schemas.UserSchema import AuthUser, UserCreate
from sqlalchemy.exc import IntegrityError
from exceptions.db_exceptions import handle_integrity_error  # <-- asegúrate de que esto exista
from fastapi import HTTPException

from models.entities import Rol  # <- Importamos el modelo Rol



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