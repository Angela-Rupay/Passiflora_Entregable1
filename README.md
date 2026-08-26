# 🌿 Pasiflora

Aplicativo móvil orientado al acompañamiento y regulación de la ansiedad, desarrollado para la asignatura de Desarrollo de Dispositivos Móviles mediante Android Studio.

## 👩‍💻 Integrantes

- *ANGELA SOFIA RUPAY*  
  Código: *20242227383*

- *VALERIA VARGAS ARTUNDUAGA*  
  Código: *20242229393*

---

## 📱 Descripción del proyecto

*Pasiflora* es una aplicación móvil enfocada inicialmente en la ansiedad. Su propósito es brindar al usuario un espacio tranquilo que permita conocer su nivel de ansiedad y posteriormente ofrecer herramientas de apoyo para su regulación.

El proyecto contempla una futura integración con la *USAP (Unidad de Salud y Atención Psicológica)* de la Universidad Surcolombiana, permitiendo complementar el acompañamiento realizado por profesionales.

Para este primer entregable se implementa principalmente el flujo de registro y evaluación inicial mediante la escala *GAD-7*.

---

## 🔄 Flujo de la aplicación

El flujo desarrollado para el primer entregable es:

```text
┌─────────────────────────┐
│  Activity 1             │
│  Bienvenida / Registro  │
└────────────┬────────────┘
             │
             │ Intent + Bundle
             ▼
┌─────────────────────────┐
│  Activity 2             │
│  Formulario GAD-7       │
└────────────┬────────────┘
             │
             │ Intent + Bundle
             ▼
┌─────────────────────────┐
│  Activity 3             │
│  Resultado GAD-7        │
└─────────────────────────┘
