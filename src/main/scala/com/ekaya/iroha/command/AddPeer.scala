package com.ekaya.iroha.command

object AddPeer {
  def apply() {
    iroha.protocol.AddPeer(Some(iroha.protocol.Peer()))
  }
}
