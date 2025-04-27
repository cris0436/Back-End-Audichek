from pydantic import BaseModel
from datetime import date

# Nuevo esquema para la persona relacionada
class PersonData(BaseModel):
    name: str
    email: str
    role: str
    birth_date: date

    class Config:
        orm_mode = True

# Esquema de creación de usuario
class UserCreate(BaseModel):
    name: str
    email: str
    role: str
    birth_date: date
    username: str
    password: str
    rol: str

# Esquema de salida de usuario
class UserOut(BaseModel):
    id: int
    username: str
    rol: str
    person: PersonData  # Relacionamos aquí el objeto person

    class Config:
        orm_mode = True