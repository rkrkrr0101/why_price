package rkrk.whyprice.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import rkrk.whyprice.member.adapter.out.api.assetfetcher.AssetFetcher

@Entity
class KoreanStock(
    crNo: String,
    name: String,
    id: Long = 0,
) : Asset {
    @Column(unique = true)
    var crNo = crNo
        protected set

    @Column(unique = true)
    var name = name
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        private set

    @Transient
    private var dataMap: MutableMap<String, String> = hashMapOf()

    override fun fetchData(assetFetchers: List<AssetFetcher>) {
        dataMap.clear()
        assetFetchers.forEach {
            val insertMap = it.fetch(crNo)
            insertData(insertMap)
        }
    }

    private fun insertData(insertMap: Map<String, String>) {
        dataMap += insertMap
    }

    override fun isDataEmpty(): Boolean = dataMap.isEmpty()

    override fun retrieveData(): Map<String, String> = dataMap

    override fun getIdentityCode(): String = crNo

    override fun getAssetName(): String = name
}
