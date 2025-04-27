from sqlalchemy.orm import Session
from models.User import User
from models.Person import Person
from schemas.UserSchema import UserCreate
from sqlalchemy.exc import IntegrityError
from exceptions.db_exceptions import handle_integrity_error  # <-- asegúrate de que esto exista
from fastapi import HTTPException

# Crear un usuario
def create_user(db: Session, user_data: UserCreate):
    try:
        # Crear primero la persona
        new_person = Person(
            name=user_data.name,
            email=user_data.email,
            role=user_data.role,
            birth_date=user_data.birth_date
        )
        db.add(new_person)
        db.commit()
        db.refresh(new_person)
        print(f"Persona creada con ID: {new_person.id}")  # Añadimos un print para verificar

        # Luego crear el usuario (ligado a la persona)
        new_user = User(
            id=new_person.id,
            username=user_data.username,
            password=user_data.password,
            rol=user_data.rol
        )
        db.add(new_user)
        db.commit()
        db.refresh(new_user)
        print(f"Usuario creado con ID: {new_user.id}")  # Añadimos otro print

        return new_user

    except IntegrityError as e:
        db.rollback()
        print(f"Error de integridad: {e}")  # Imprimimos el error para ver los detalles
        handle_integrity_error(e)
    except Exception as e:
        db.rollback()
        print(f"Otro error: {e}") # Capturamos otros errores para ver qué sucede
        raise HTTPException(status_code=500, detail=f"Error inesperado al crear el usuario: {e}")

# Obtener un usuario por ID
def get_user(db: Session, user_id: int):
    return db.query(User).filter(User.id == user_id).first()

# Obtener todos los usuarios
def get_all_users(db: Session):
    return db.query(User).all()

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