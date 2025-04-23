from sqlalchemy import Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import declared_attr
from models.Person import Person

Base = declarative_base()

class Usuario(Person):
    __tablename__ = 'users'
    id = Column(String(10), primary_key=True, index=True)
    username = Column(String, unique=True, nullable=False)
    password = Column(String, nullable=False)
    rol = Column(String, nullable=False)  # 'admin' o 'patient'
    