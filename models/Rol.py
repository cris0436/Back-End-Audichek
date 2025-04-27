from sqlalchemy import Column, Integer, String
from database.connections import Base


class Rol(Base):
    __tablename__ = 'rols'
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(50), unique=True, index=True)
    description = Column(String(100), nullable=False)
