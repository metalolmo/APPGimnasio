package es.ua.eps.gimnasioapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RutinasPredefinidasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutinas_predefinidas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerRutinasVideo)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val ejercicios = listOf(
            EjercicioVideo(
                "Empuje",
                "Entrenamiento para pecho, tríceps y hombros.",
                "b6ouj88iBZs",
                listOf(
                    "Press de banca plano – 4x10",
                    "Fondos en paralelas – 3x12",
                    "Press militar con barra – 4x8",
                    "Aperturas con mancuernas – 3x12",
                    "Extensiones de tríceps en polea – 3x15"
                )
            ),
            EjercicioVideo(
                "Tirón",
                "Trabajo de espalda y bíceps.",
                "DXL18E7QRbk",
                listOf(
                    "Dominadas pronadas – 4x8",
                    "Remo con barra – 4x10",
                    "Jalón al pecho – 3x12",
                    "Curl de bíceps con barra – 3x10",
                    "Curl martillo – 3x12"
                )
            ),
            EjercicioVideo(
                "Pierna",
                "Sesión completa de tren inferior.",
                "H6mRkx1x77k",
                listOf(
                    "Sentadillas con barra – 4x10",
                    "Prensa de piernas – 3x12",
                    "Zancadas con mancuernas – 3x10 por pierna",
                    "Peso muerto rumano – 4x8",
                    "Elevación de talones (gemelos) – 3x20"
                )
            ),
            EjercicioVideo(
                "Full Body",
                "Cuerpo completo, ideal para días con poco tiempo.",
                "nxisr1AalNc",
                listOf(
                    "Sentadilla frontal – 4x10",
                    "Press banca inclinado – 4x8",
                    "Peso muerto – 3x8",
                    "Remo con mancuerna – 3x10",
                    "Planchas – 3x45 segundos"
                )
            ),
            EjercicioVideo(
                "HIIT",
                "Rutina intensa de cuerpo completo en intervalos.",
                "8UVoNhapb_Q",
                listOf(
                    "Jumping jacks – 40s trabajo / 20s descanso",
                    "Burpees – 40s / 20s",
                    "Sentadilla con salto – 40s / 20s",
                    "Mountain climbers – 40s / 20s",
                    "Abdominales en bicicleta – 40s / 20s"
                )
            )
        )

        recyclerView.adapter = EjercicioVideoAdapter(this, ejercicios)
    }
}
