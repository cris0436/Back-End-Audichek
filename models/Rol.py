from sqlalchemy import Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import declared_attr
from models.Person import Person
Base = declarative_base()

class Rol(Base):
    __tablename__ = 'rols'
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(50), unique=True, index=True)
    description = Column(String(100), nullable=False)
