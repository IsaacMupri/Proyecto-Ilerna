package com.example.proyectoilerna.util

import android.content.Context
import com.example.proyectoilerna.mode.Pedido
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimeManager(context: Context) {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("contacts")
    private val authManager = AuthManager(context)

    fun addContact(Pedido: Pedido) {
        val key = databaseReference.push().key
        if (key != null) {
            databaseReference.child(key).setValue(Pedido)
        }
    }

    fun deleteContact(contactId: String) {
        databaseReference.child(contactId).removeValue()
    }

    fun updateContact(contactId: String, updatedContact: Pedido) {
        databaseReference.child(contactId).setValue(updatedContact)
    }

    fun getContactsFlow(): Flow<List<Pedido>> {
        val idFilter = authManager.getCurrentUser()?.uid?.toInt()
        val flow = callbackFlow {
            val listener = databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val contacts = snapshot.children.mapNotNull { snapshot ->
                        val pedido = snapshot.getValue(Pedido::class.java)
                        snapshot.key?.let { pedido?.copy(key = it) }
                    }
                    trySend(contacts.filter { it.uid == idFilter }).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
            awaitClose { databaseReference.removeEventListener(listener) }
        }
        return flow
    }
}