from pydantic import BaseModel, EmailStr
from typing import List, Optional
from datetime import datetime
class UserBase(BaseModel):
    email: EmailStr; full_name: str; phone_number: str
class UserCreate(UserBase):
    password: str
class User(UserBase):
    id: int; is_active: bool
    class Config: from_attributes = True
class EmergencyAlertCreate(BaseModel):
    latitude: float; longitude: float
class EmergencyAlert(EmergencyAlertCreate):
    id: int; user_id: int; timestamp: datetime; status: str
    class Config: from_attributes = True
class Token(BaseModel):
    access_token: str; token_type: str
