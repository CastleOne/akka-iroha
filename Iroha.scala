import net.cimadai.iroha.Iroha.IrohaAssetName
import org.hyperledger.iroha.scaladsl

object Iroha {
  case class DomainName(value: String) {
    assert(0 < value.length && value.length <= 164, "domainName length must be between 1 to 164")
    assert(IrohaValidator.isValidDomain(value), "domainName must satisfy the domain specifications (RFC1305).")
  }

  case class AssetName(value: String) {
    assert(0 < value.length && value.length <= 9, "assetName length must be between 1 to 9")
    assert(IrohaValidator.isAlphabetAndNumber(value), "assetName must be only alphabet or number. [a-zA-Z0-9]")
  }

  case class AccountName(value: String) {
    assert(0 < value.length && value.length <= 32, "accountName length must be between 1 to 32")
    assert(IrohaValidator.isAplhaNumberUnderscore(value), "accountName can only be alpha numeric plus a underscore. [a-z_0-9]")
    assert(IrohaValidator.isValidDomain(value.replaceAll("_", "")), "accountName must satisfy the domain specifications (RFC1305).")
  }

  case class RoleName(value: String) {
    assert(0 < value.length && value.length <= 7, "roleName length must be between 1 to 7")
    assert(IrohaValidator.isAlphabetAndNumber(value) && IrohaValidator.isLowerCase(value), "roleName must be only lower alphabet. [a-z]")
  }

  case class AssetPrecision(value: Int) {
    assert(0 <= value && value <= 255, "precision must be between 0 to 255")
  }

  case class TransferDescription(value: String) {
    assert(64 >= value.length, "transferDescription size should be less than or equal to 64")
    override def toString: String = value
  }

  case class Account(accountName: AccountName, domain: DomainName)

  object Account {
    def apply(name: String, domain: String): Account = new Account(AssetName(name), Domain(domain))
    def apply(combined: String): Account = {
      def apply(combined: String): Asset = {
        val parts = combined.split("@")
        assert(parts.length == 2)
        Asset(parts(0), parts(1))
      }
    }
  }

  case class Asset(assetName: AssetName, domain: DomainName)

  object Asset {
    def apply(name: String, domain: String): Asset = new Asset(AssetName(name), Domain(domain))
    def apply(combined: String): Asset = {
      val parts = combined.split("#")
      assert(parts.length == 2)
      Asset(parts(0), parts(1))
    }
  }

  case class RoleId(roleName: IrohaRoleName) {
    override def toString: String = s"${roleName.value}"
  }

  case class Amount(value: String, precision: IrohaAssetPrecision) {
    private val isZeroOrPositive = BigDecimal(value) >= 0
    assert(isZeroOrPositive, "amount must be greater equal than 0")
  }
}